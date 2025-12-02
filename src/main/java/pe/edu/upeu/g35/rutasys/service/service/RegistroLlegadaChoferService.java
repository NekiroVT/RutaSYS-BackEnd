package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo; // Importar para createInitialRegistro
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AsistenciaEstadoDTO; // ⬅️ DEBE ESTAR IMPORTADO
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface RegistroLlegadaChoferService extends GenericService<RegistroLlegadaChofer, Long> {

    // --- Métodos DTO de presentación ---
    Optional<RegistroLlegadaChoferDTO> getRegistroLlegadaChoferDTO(Long id);
    List<RegistroLlegadaChoferDTO> getAllRegistros();

    // --- 🚀 MÉTODO CLAVE: Dispara Asistencia Masiva por Vehículo (Usado por el Controller) ---
    RegistroLlegadaChoferDTO register(RegistroLlegadaChoferRegisterRequestDTO requestDTO);

    // 🚀 LÍNEA A AGREGAR
    Optional<AsistenciaEstadoDTO> getEstadoAsistenciaDominante(Long idChofer);

    // --- Métodos de ciclo de vida (Administrativos) ---
    /**
     * Crea la entrada inicial en la tabla RegistroLlegadaChofer con estado "NO_INICIADO"
     * por cada asignación Manifiesto-Vehículo.
     */
    RegistroLlegadaChoferDTO createInitialRegistro(ManifiestoVehiculo mv);

    /**
     * Habilita la asistencia, cambiando el estado de un registro de "NO_INICIADO" a "ACTIVO",
     * afectando a todos los registros asociados al mismo vehículo.
     */
    void habilitarAsistencia(Long idManifiestoVehiculo);

    /**
     * Marca todas las asistencias pendientes ("ACTIVO" o "NO_INICIADO") de un vehículo
     * como "FALTO".
     */
    List<RegistroLlegadaChoferDTO> marcarComoFaltoPorVehiculo(Long idVehiculo);

    // --- Métodos de búsqueda especializados ---
    Optional<RegistroLlegadaChoferDTO> findByManifiestoVehiculoIdDTO(Long idManifiestoVehiculo);
    List<RegistroLlegadaChoferDTO> findByChoferIdDTO(Long idChofer);
}