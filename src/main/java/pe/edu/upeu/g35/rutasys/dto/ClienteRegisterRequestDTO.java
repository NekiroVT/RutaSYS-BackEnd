package pe.edu.upeu.g35.rutasys.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

// DTO para la solicitud de registro de un nuevo Cliente.
// Contiene los datos mínimos necesarios para crear un registro en la tabla CLIENTE
// MÁS los campos de Usuario requeridos por el AuthService.
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteRegisterRequestDTO {

    // --- Campos de Usuario para AuthService ---
    private String username;
    private String password;

    // --- Campos de Cliente ---
    // RUC (Registro Único de Contribuyentes) - 11 dígitos
    private String ruc;

    // Razón Social o Nombre de la Empresa
    private String razonSocial;

    // Dirección fiscal o principal
    private String direccion;

    // Nombre de la persona de contacto
    private String contacto;

    // Teléfono de contacto
    private String telefono;

    // Correo electrónico de contacto
    private String correo;
}