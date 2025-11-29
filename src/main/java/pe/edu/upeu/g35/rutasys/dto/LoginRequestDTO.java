package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;

@Data
public class LoginRequestDTO {

    // El nombre de usuario que el chófer ingresa
    private String username;

    // La contraseña en texto plano que el chófer ingresa
    private String password;
}