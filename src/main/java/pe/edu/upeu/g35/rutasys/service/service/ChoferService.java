package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.dto.ChoferDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService; // ⬅️ Importación de la Interfaz Base

import java.util.List;
import java.util.Optional;

// 🟢 Extiende el servicio genérico para heredar save, findById, delete y findAll.
public interface ChoferService extends GenericService<Chofer, Long> {

    // 1. Método para registrar un chofer (Lógica de negocio especializada/DTO)
    ChoferDTO registerChofer(Chofer chofer);

    // 2. Método para obtener un chofer por ID (Retornando DTO)
    ChoferDTO getChofer(Long id);

    // 3. Método para obtener todos los choferes (Retornando lista de DTOs)
    List<ChoferDTO> getAllChoferes();

    // 4. Búsqueda especializada por DNI (Método que no es CRUD básico)
    Optional<ChoferDTO> findByDni(String dni);


    // ❌ MÉTODOS ELIMINADOS DE AQUÍ PORQUE SON HEREDADOS DE GenericService:
    // ----------------------------------------------------------------------
    // Chofer save(Chofer chofer);
    // Optional<Chofer> findById(Long id);
    // void delete(Long id);
    // ----------------------------------------------------------------------
}