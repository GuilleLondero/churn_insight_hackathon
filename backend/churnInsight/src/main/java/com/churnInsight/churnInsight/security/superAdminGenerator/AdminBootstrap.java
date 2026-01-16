package com.churnInsight.churnInsight.security.superAdminGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.churnInsight.churnInsight.domain.model.Rol;
import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.repository.UsuarioRepository;

@Component
@Profile("docker")

public class AdminBootstrap implements CommandLineRunner {
    
    @Autowired
    private UsuarioRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${SUPER_PASSWORD}")
    private String rawPassword;

  @Override
  public void run(String... args) {
    if (!userRepository.existsByRol(Rol.SUPER_ADMIN)) {
        Usuario usuario = new Usuario();
        usuario.setUsuario("super");
        usuario.setPassword(passwordEncoder.encode(rawPassword));// se setea la password encriptada
        usuario.setEmail("superUser@churninsight.com");
        usuario.setRol(Rol.SUPER_ADMIN);
        userRepository.save(usuario);
    }
  }
}