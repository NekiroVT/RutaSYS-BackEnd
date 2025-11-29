package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.repository.VehiculoRepository;
import pe.edu.upeu.g35.rutasys.mappers.VehiculoMapper;
import pe.edu.upeu.g35.rutasys.service.service.VehiculoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper) {
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
    }

    // --- MÉTODOS CRUD y DTO ---

    @Override
    public Vehiculo save(Vehiculo vehiculo) { return vehiculoRepository.save(vehiculo); }
    @Override
    public Optional<Vehiculo> findById(Long id) { return vehiculoRepository.findById(id); }
    @Override
    public List<Vehiculo> findAll() { return vehiculoRepository.findAll(); }
    @Override
    public void delete(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("Vehiculo con ID " + id + " no encontrado.");
        }
        vehiculoRepository.deleteById(id);
    }

    @Override
    public Optional<VehiculoDTO> getVehiculoDTO(Long id) {
        return vehiculoRepository.findById(id).map(vehiculoMapper::toDTO);
    }
    @Override
    public List<VehiculoDTO> getAllVehiculos() {
        return vehiculoRepository.findAll().stream().map(vehiculoMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<VehiculoDTO> findByPlacaDTO(String placa) {
        return vehiculoRepository.findByPlaca(placa).map(vehiculoMapper::toDTO);
    }
}