package com.churnInsight.churnInsight.security;

import com.churnInsight.churnInsight.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Buscamos el usuario en la Base de datos
        com.churnInsight.churnInsight.entity.Usuario usuario = usuarioRepository.findByUsuario(username)
        .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        // El objeto de seguridad
        return org.springframework.security.core.userdetails.User
        .builder()
        .username(usuario.getUsuario()) 
        .password(usuario.getPassword()) 
        .roles(usuario.getRol().name())  
        .build();
    }
}
