package com.ventas.SistemaVentas.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Obtenemos el header de autorización (Donde viene el token)
        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // 2. Verificamos si el header existe y empieza con "Bearer "
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Quitamos la palabra "Bearer " para dejar solo el token
            username = jwtUtils.extractUsername(token); // Extraemos el usuario del token
        }

        // 3. Si encontramos usuario y nadie está autenticado aún en el contexto...
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Cargamos los datos del usuario desde la BD
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // 4. Validamos si el token es correcto y no ha expirado
            if (jwtUtils.validateToken(token, userDetails.getUsername())) {

                // 5. Creamos la autenticación y la guardamos en el contexto de Spring
                // (Esto es como ponerle el sello de "APROBADO" a la petición)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // 6. Dejamos que la petición continúe su camino
        filterChain.doFilter(request, response);
    }
}