package com.churnInsight.churnInsight.entity;

import com.churnInsight.churnInsight.domain.dto.UsuarioDTO;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data 
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String password; // Contraseña encriptada

    @Column(unique = true, nullable = false)
    private String email;

    public Usuario(UsuarioDTO user){
        this.id = user.getId();
        this.usuario = user.getUsuario();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
