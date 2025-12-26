package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
public class UserController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    // READ 
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // UPDATE 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody Usuario usuarioDetalles) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setUsuario(usuarioDetalles.getUsuario());
        usuario.setEmail(usuarioDetalles.getEmail());
        // Se encripta la nueva contraseña
        if(usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()){
            usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
        }

        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuario actualizado");
    }

    // DELETE 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}