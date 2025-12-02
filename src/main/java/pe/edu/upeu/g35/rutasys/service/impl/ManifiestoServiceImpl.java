package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.repository.ManifiestoRepository;
import pe.edu.upeu.g35.rutasys.repository.ClienteRepository; // Necesario para buscar Cliente
import pe.edu.upeu.g35.rutasys.mappers.ManifiestoMapper;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManifiestoServiceImpl implements ManifiestoService {

    private final ManifiestoRepository manifiestoRepository;
    private final ManifiestoMapper manifiestoMapper;
    private final ClienteRepository clienteRepository; // Inyectar Repositorio de Cliente

    public ManifiestoServiceImpl(ManifiestoRepository manifiestoRepository, ManifiestoMapper manifiestoMapper, ClienteRepository clienteRepository) {
        this.manifiestoRepository = manifiestoRepository;
        this.manifiestoMapper = manifiestoMapper;
        this.clienteRepository = clienteRepository;
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public Manifiesto save(Manifiesto manifiesto) {
        return manifiestoRepository.save(manifiesto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Manifiesto> findById(Long id) { return manifiestoRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Manifiesto> findAll() { return manifiestoRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!manifiestoRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Manifiesto con ID " + id + " no encontrado.");
        }
        manifiestoRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<ManifiestoDTO> getManifiestoDTO(Long id) {
        return manifiestoRepository.findById(id).map(manifiestoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoDTO> getAllManifiestos() {
        return manifiestoRepository.findAll().stream().map(manifiestoMapper::toDTO).collect(Collectors.toList());
    }

    // --- 1. MÉTODO DE REGISTRO (Lógica de negocio especializada) ---

    @Override
    @Transactional
    public ManifiestoDTO register(ManifiestoRegisterRequestDTO requestDTO) {

        // 1. Validar unicidad del código de manifiesto (si es único)
        if (manifiestoRepository.findByCodManifiesto(requestDTO.getCodManifiesto()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un manifiesto con el código: " + requestDTO.getCodManifiesto());
        }

        // 2. Obtener la entidad Cliente
        Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente con ID " + requestDTO.getIdCliente() + " no encontrado."));

        // 3. Mapear el DTO a la entidad Manifiesto (ignora ID, estado, fechaSolicitud)
        Manifiesto manifiesto = manifiestoMapper.toEntity(requestDTO);

        // 4. Asignar las referencias y campos automáticos
        manifiesto.setCliente(cliente);
        manifiesto.setFechaSolicitud(LocalDate.now()); // Fecha de creación
        manifiesto.setEstado("PENDIENTE"); // Estado inicial

        // 5. Guardar y retornar DTO
        Manifiesto savedManifiesto = manifiestoRepository.save(manifiesto);
        return manifiestoMapper.toDTO(savedManifiesto);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public Optional<ManifiestoDTO> findByCodManifiestoDTO(String codManifiesto) {
        return manifiestoRepository.findByCodManifiesto(codManifiesto).map(manifiestoMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoDTO> findByClienteIdDTO(Long idCliente) {
        return manifiestoRepository.findByCliente_Id(idCliente).stream()
                .map(manifiestoMapper::toDTO)
                .collect(Collectors.toList());
    }
}