package com.churnInsight.churnInsight.domain.dto;

import com.churnInsight.churnInsight.entity.Usuario;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRespuestaDTO {

    private Long id;
    private String usuario;
    private String email;

    public UsuarioRespuestaDTO(Usuario usuario){
        this.id = usuario.getId();
        this.usuario = usuario.getUsuario();
        this.email = usuario.getEmail();
    }

}
