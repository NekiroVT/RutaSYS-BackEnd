package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface VehiculoService extends GenericService<Vehiculo, Long> {

    Optional<VehiculoDTO> getVehiculoDTO(Long id);

    List<VehiculoDTO> getAllVehiculos();

    Optional<VehiculoDTO> findByPlacaDTO(String placa);
}