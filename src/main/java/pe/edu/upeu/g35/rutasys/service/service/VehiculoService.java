package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.VehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface VehiculoService extends GenericService<Vehiculo, Long> {

    // --- Métodos DTO de presentación ---
    Optional<VehiculoDTO> getVehiculoDTO(Long id);
    List<VehiculoDTO> getAllVehiculos();

    // --- Método de Registro (Lógica de negocio especializada) ---
    VehiculoDTO register(VehiculoRegisterRequestDTO requestDTO);

    // --- Métodos de búsqueda especializados ---
    Optional<VehiculoDTO> findByPlacaDTO(String placa);
    List<VehiculoDTO> findByEstadoDTO(String estado);
}