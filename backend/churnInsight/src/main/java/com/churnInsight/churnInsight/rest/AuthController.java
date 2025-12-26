package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.repository.UsuarioRepository;
import com.churnInsight.churnInsight.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Endpoint para Registar
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        // encripta la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioRepository.save(usuario);
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado con éxito: " + usuario.getUsuario());
        return ResponseEntity.ok(response);
    }

    // Endpoint para LOGIN y obtener TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Usuario request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getPassword())
        );
        
        // Se busca al usuario y se genera el token
        Usuario user = usuarioRepository.findByUsuario(request.getUsuario()).orElseThrow();
        
        // Convertimos a UserDetails
        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsuario())
                .password(user.getPassword())
                .roles("USER")
                .build();
                
        String token = jwtService.generateToken(userDetails);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}