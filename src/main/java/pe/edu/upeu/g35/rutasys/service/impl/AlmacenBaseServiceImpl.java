package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.AlmacenBase;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseDTO;
import pe.edu.upeu.g35.rutasys.dto.AlmacenBaseRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.repository.AlmacenBaseRepository;
import pe.edu.upeu.g35.rutasys.mappers.AlmacenBaseMapper;
import pe.edu.upeu.g35.rutasys.service.service.AlmacenBaseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlmacenBaseServiceImpl implements AlmacenBaseService {

    private final AlmacenBaseRepository almacenBaseRepository;
    private final AlmacenBaseMapper almacenBaseMapper;

    public AlmacenBaseServiceImpl(AlmacenBaseRepository almacenBaseRepository, AlmacenBaseMapper almacenBaseMapper) {
        this.almacenBaseRepository = almacenBaseRepository;
        this.almacenBaseMapper = almacenBaseMapper;
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public AlmacenBase save(AlmacenBase entity) { return almacenBaseRepository.save(entity); }

    @Override
    @Transactional(readOnly = true)
    public Optional<AlmacenBase> findById(Long id) { return almacenBaseRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<AlmacenBase> findAll() { return almacenBaseRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!almacenBaseRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Almacén Base con ID " + id + " no encontrado.");
        }
        almacenBaseRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<AlmacenBaseDTO> getAlmacenBaseDTO(Long id) {
        return almacenBaseRepository.findById(id).map(almacenBaseMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AlmacenBaseDTO> getAllAlmacenesBase() {
        return almacenBaseRepository.findAll().stream().map(almacenBaseMapper::toDTO).collect(Collectors.toList());
    }

    // --- 1. MÉTODO DE REGISTRO (CREATE) ---

    @Override
    @Transactional
    public AlmacenBaseDTO register(AlmacenBaseRegisterRequestDTO requestDTO) {

        // 1. Validar unicidad del nombre
        if (almacenBaseRepository.findByNombre(requestDTO.getNombre()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un almacén base con el nombre: " + requestDTO.getNombre());
        }

        // 2. Mapear y guardar
        AlmacenBase nuevoAlmacen = almacenBaseMapper.toEntity(requestDTO);

        AlmacenBase savedAlmacen = almacenBaseRepository.save(nuevoAlmacen);
        return almacenBaseMapper.toDTO(savedAlmacen);
    }

    // --- 2. MÉTODO DE ACTUALIZACIÓN (UPDATE) ---

    @Override
    @Transactional
    public AlmacenBaseDTO update(Long id, AlmacenBaseDTO updateDTO) {

        AlmacenBase existingAlmacen = almacenBaseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Almacén Base con ID " + id + " no encontrado para actualizar."));

        // 1. Validar unicidad del nombre si se intenta cambiar
        if (!existingAlmacen.getNombre().equals(updateDTO.getNombre())) {
            if (almacenBaseRepository.findByNombre(updateDTO.getNombre()).isPresent()) {
                throw new IllegalArgumentException("El nombre '" + updateDTO.getNombre() + "' ya está siendo usado por otro almacén.");
            }
        }

        // 2. Mapear DTO a la entidad existente (MapStruct se encarga de transferir campos)
        updateDTO.setId(id); // Asegurar que el ID del DTO sea el correcto
        AlmacenBase updatedAlmacen = almacenBaseMapper.toEntity(updateDTO);

        // 3. Guardar y retornar DTO
        AlmacenBase savedAlmacen = almacenBaseRepository.save(updatedAlmacen);
        return almacenBaseMapper.toDTO(savedAlmacen);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public Optional<AlmacenBaseDTO> findByNombreDTO(String nombre) {
        return almacenBaseRepository.findByNombre(nombre).map(almacenBaseMapper::toDTO);
    }
}