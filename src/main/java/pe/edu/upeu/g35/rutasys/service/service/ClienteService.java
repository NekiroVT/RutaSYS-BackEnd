package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService; // ⬅️ Importación de la Interfaz Base

import java.util.List;
import java.util.Optional;

// 🟢 Extiende el servicio genérico para heredar save, findById, delete y findAll.
public interface ClienteService extends GenericService<Cliente, Long> {

    // --- Métodos DTO de presentación ---
    Optional<ClienteDTO> getClienteDTO(Long id); // Obtiene un cliente por ID (Retornando DTO)
    List<ClienteDTO> getAllClientes(); // Obtiene todos los clientes (Retornando lista de DTOs)

    // 1. Método para registrar un cliente (Lógica de negocio especializada/DTO)
    // Se asume que el mapeo del RequestDTO a Entidad Cliente se realiza antes de llamar aquí.
    ClienteDTO registerCliente(Cliente cliente);

    // 2. Búsqueda especializada por RUC (Método que no es CRUD básico)
    Optional<ClienteDTO> findByRucDTO(String ruc);

    // --- Métodos de búsqueda adicionales ---
    List<ClienteDTO> findByRazonSocialContaining(String razonSocial);
    List<ClienteDTO> findByEstadoDTO(String estado);
}