package com.churnInsight.churnInsight.entity;

import com.churnInsight.churnInsight.domain.dto.UsuarioDTO;
import com.churnInsight.churnInsight.domain.model.Rol;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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

    // Agregamos el atributo rol a la entidad usuario 
    // Guarda el nombre "USUARIO" en la Base de Datos en lugar de un número
    @Enumerated(EnumType.STRING)
    @Column(name = "rol", nullable = false)
    private Rol rol = Rol.USUARIO;




    public Usuario(UsuarioDTO user){
        this.id = user.getId();
        this.usuario = user.getUsuario();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }
}
