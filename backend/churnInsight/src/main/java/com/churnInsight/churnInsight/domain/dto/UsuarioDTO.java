package com.churnInsight.churnInsight.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]{4,8}$", message = "El usuario debe tener entre 4 y 8 caracteres alfanumericos!")
    private String usuario;
    @NotBlank
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email invalido!")
    private String email;
    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[^A-Za-z0-9]).{6}$", 
    message = "La password debe ser de 6 caracteres y debe contener al menos: 1 mayuscula, 1 minuscula, 1 numero y 1 simbolo!")
    private String password;
}
