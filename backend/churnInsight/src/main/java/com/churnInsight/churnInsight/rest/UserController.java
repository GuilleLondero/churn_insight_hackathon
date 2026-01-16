package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.UsuarioDTO;
import com.churnInsight.churnInsight.domain.dto.UsuarioRespuestaDTO;
import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.service.UsuarioService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://127.0.0.1:5500/", "https://churninsight.netlify.app/"})
public class UserController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;

    // READ
    @GetMapping
    public List<UsuarioRespuestaDTO> getAllUsuarios() {
        return usuarioService.getAll().stream().map(u -> new UsuarioRespuestaDTO(u)).toList(); // Se pasa de clase usuario
                                                                                               //a usuario de respuesta (no muestra la contraseña)
    }

    // UPDATE 
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUsuario(@PathVariable Long id, @RequestBody @Valid UsuarioDTO usuarioDetalles) {
        Usuario usuario = usuarioService.getUsuarioById(id);

        usuario.setUsuario(usuarioDetalles.getUsuario());
        usuario.setEmail(usuarioDetalles.getEmail());
        // Se encripta la nueva contraseña
        if(usuarioDetalles.getPassword() != null && !usuarioDetalles.getPassword().isEmpty()){
            usuario.setPassword(passwordEncoder.encode(usuarioDetalles.getPassword()));
        }

        usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok("Usuario actualizado");
    }

    // DELETE 
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUsuario(@PathVariable Long id) {
        usuarioService.deleteUsuarioById(id);
        return ResponseEntity.ok("Usuario eliminado");
    }
}