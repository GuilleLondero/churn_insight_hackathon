package com.churnInsight.churnInsight.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    private Long id;
    @NotBlank
    private String usuario;
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
