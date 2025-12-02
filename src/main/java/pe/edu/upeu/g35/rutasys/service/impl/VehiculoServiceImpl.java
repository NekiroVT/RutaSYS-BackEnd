package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.entity.AlmacenBase; // ⬅️ CORREGIDO: Usar AlmacenBase
import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.VehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.repository.VehiculoRepository;
import pe.edu.upeu.g35.rutasys.repository.AlmacenBaseRepository; // ⬅️ CORREGIDO: Usar AlmacenBaseRepository
import pe.edu.upeu.g35.rutasys.mappers.VehiculoMapper;
import pe.edu.upeu.g35.rutasys.service.service.VehiculoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VehiculoServiceImpl implements VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final VehiculoMapper vehiculoMapper;
    private final AlmacenBaseRepository almacenBaseRepository; // ⬅️ CORREGIDO: Inyectar Repositorio de AlmacenBase

    public VehiculoServiceImpl(VehiculoRepository vehiculoRepository, VehiculoMapper vehiculoMapper, AlmacenBaseRepository almacenBaseRepository) { // ⬅️ CORREGIDO
        this.vehiculoRepository = vehiculoRepository;
        this.vehiculoMapper = vehiculoMapper;
        this.almacenBaseRepository = almacenBaseRepository; // ⬅️ ASIGNACIÓN CORREGIDA
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public Vehiculo save(Vehiculo vehiculo) {
        return vehiculoRepository.save(vehiculo);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Vehiculo> findById(Long id) { return vehiculoRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Vehiculo> findAll() { return vehiculoRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!vehiculoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Vehículo con ID " + id + " no encontrado.");
        }
        vehiculoRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<VehiculoDTO> getVehiculoDTO(Long id) {
        return vehiculoRepository.findById(id).map(vehiculoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> getAllVehiculos() {
        return vehiculoRepository.findAll().stream().map(vehiculoMapper::toDTO).collect(Collectors.toList());
    }

    // --- 1. MÉTODO DE REGISTRO (Lógica de negocio especializada) ---

    @Override
    @Transactional
    public VehiculoDTO register(VehiculoRegisterRequestDTO requestDTO) {

        // 1. Validar unicidad de la placa
        if (vehiculoRepository.findByPlaca(requestDTO.getPlaca()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un vehículo registrado con la placa: " + requestDTO.getPlaca());
        }

        // 2. Obtener la entidad AlmacenBase
        AlmacenBase almacenBase = almacenBaseRepository.findById(requestDTO.getIdAlmacenBase()) // ⬅️ CORREGIDO
                .orElseThrow(() -> new IllegalArgumentException("Almacén base con ID " + requestDTO.getIdAlmacenBase() + " no encontrado.")); // ⬅️ CORREGIDO

        // 3. Mapear el DTO a la entidad Vehiculo (ignora ID, estado, almacenBase)
        Vehiculo vehiculo = vehiculoMapper.toEntity(requestDTO);

        // 4. Asignar las referencias y campos automáticos
        vehiculo.setAlmacenBase(almacenBase); // ⬅️ CORREGIDO: Usar almacenBase
        // El estado del DTO tiene prioridad, si no existe, usa 'OPERATIVO'
        vehiculo.setEstado(requestDTO.getEstado() != null ? requestDTO.getEstado() : "OPERATIVO");

        // 5. Guardar y retornar DTO
        Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);
        return vehiculoMapper.toDTO(savedVehiculo);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public Optional<VehiculoDTO> findByPlacaDTO(String placa) {
        return vehiculoRepository.findByPlaca(placa).map(vehiculoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VehiculoDTO> findByEstadoDTO(String estado) {
        return vehiculoRepository.findByEstado(estado).stream()
                .map(vehiculoMapper::toDTO)
                .collect(Collectors.toList());
    }
}