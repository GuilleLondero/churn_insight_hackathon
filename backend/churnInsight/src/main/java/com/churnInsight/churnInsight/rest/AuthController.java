package com.churnInsight.churnInsight.rest;

import com.churnInsight.churnInsight.domain.dto.UsuarioDTO;
import com.churnInsight.churnInsight.domain.dto.UsuarioLoginDTO;
import com.churnInsight.churnInsight.entity.Usuario;
import com.churnInsight.churnInsight.security.JwtService;
import com.churnInsight.churnInsight.service.UsuarioService;

import jakarta.validation.Valid;
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
@CrossOrigin(origins = "http://127.0.0.1:5500/")
public class AuthController {

    private final UsuarioService usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    // Endpoint para Registar
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsuarioDTO usuario) {
        // encripta la contraseña
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        usuarioService.crearUsuario(new Usuario(usuario));
        
        Map<String, String> response = new HashMap<>();
        response.put("mensaje", "Usuario registrado con éxito: " + usuario.getUsuario());
        return ResponseEntity.ok(response);
    }

    // Endpoint para LOGIN y obtener TOKEN
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UsuarioLoginDTO request) {//Se usa Usuario login para que la validacion
                                                                                 //no pida ingresar un email o algun otro campo con validacion.
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsuario(), request.getPassword())
        );
        
        // Se busca al usuario y se genera el token
        Usuario user = usuarioService.getByUsuario(request.getUsuario());
        System.out.println(user.getUsuario() + user.getPassword());
        
        // Convertimos a UserDetails
        var userDetails = org.springframework.security.core.userdetails.User.builder()
                .username(user.getUsuario())
                .password(user.getPassword())
                .roles(user.getRol().name())
                .build();
                
        String token = jwtService.generateToken(userDetails);
        
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return ResponseEntity.ok(response);
    }
}