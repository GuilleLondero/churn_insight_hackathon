package com.churnInsight.churnInsight.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.churnInsight.churnInsight.domain.dto.UsuarioDTO;
import com.churnInsight.churnInsight.domain.model.Rol;
import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.repository.UsuarioRepository;

@RestController
@RequestMapping("/super/adm") // Ruta base definida en el PDF [cite: 25]
public class SuperAdminController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Endpoint: POST /super/adm
    // Recibe JSON
    @PostMapping
    public ResponseEntity<?> crearNuevoAdmin(@RequestBody UsuarioDTO adminDto) {
        if (usuarioRepository.findByUsuario(adminDto.getUsuario()).isPresent()) {
            return ResponseEntity.badRequest().body("Error: El usuario ya existe");
        }

        // Constructor que esta en la entidad Usuario
        Usuario nuevoAdmin = new Usuario(adminDto);
        
        // Se encripta la contraseña 
        nuevoAdmin.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        
        // Rol ADMIN
        nuevoAdmin.setRol(Rol.ADMIN);

        usuarioRepository.save(nuevoAdmin);

        return ResponseEntity.ok("Administrador creado exitosamente: " + nuevoAdmin.getUsuario());
    }


    // Endpoint: POST /super/adm/{usuario} 
    @PostMapping("/{username}")
    public ResponseEntity<?> ascenderAAdmin(@PathVariable String username) {
        return usuarioRepository.findByUsuario(username)
                .map(usuario -> {
                    usuario.setRol(Rol.ADMIN); // Pase 
                    usuarioRepository.save(usuario);
                    return ResponseEntity.ok("Usuario " + username + " ascendido a ADMIN.");
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }

    // Endpoint: DELETE /super/adm/{usuario} 
    @DeleteMapping("/{username}")
    public ResponseEntity<?> revocarAdmin(@PathVariable String username) {
        return usuarioRepository.findByUsuario(username)
                .map(usuario -> {
                    usuario.setRol(Rol.USUARIO); 
                    usuarioRepository.save(usuario);
                    return ResponseEntity.ok("Rol ADMIN revocado a " + username + ". Ahora es USUARIO.");
                })
                .orElse(ResponseEntity.status(404).body("Usuario no encontrado"));
    }
}