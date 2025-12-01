package com.ventas.SistemaVentas.security;

import com.ventas.SistemaVentas.model.User;
import com.ventas.SistemaVentas.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 1. Buscamos el usuario en la BD por su nombre de usuario
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // 2. Retornamos un objeto User de Spring Security con los datos de nuestra BD
        // (Aquí convertimos nuestro 'User' al 'UserDetails' que entiende Spring)
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>() // Aquí irían los roles/autoridades si usáramos lógica avanzada
        );
    }
}