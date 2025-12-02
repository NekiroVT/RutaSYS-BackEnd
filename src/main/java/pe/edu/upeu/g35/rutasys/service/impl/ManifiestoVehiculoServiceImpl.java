package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.repository.ManifiestoVehiculoRepository;
import pe.edu.upeu.g35.rutasys.repository.ManifiestoRepository;
import pe.edu.upeu.g35.rutasys.repository.VehiculoRepository;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.AyudanteRepository;
import pe.edu.upeu.g35.rutasys.mappers.ManifiestoVehiculoMapper;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoVehiculoService;
import pe.edu.upeu.g35.rutasys.service.service.RegistroLlegadaChoferService; // ⬅️ IMPORTANTE
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManifiestoVehiculoServiceImpl implements ManifiestoVehiculoService {

    private final ManifiestoVehiculoRepository mvRepository;
    private final ManifiestoVehiculoMapper mvMapper;
    private final ManifiestoRepository manifiestoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ChoferRepository choferRepository;
    private final AyudanteRepository ayudanteRepository;
    private final RegistroLlegadaChoferService registroService; // ⬅️ INYECCIÓN

    public ManifiestoVehiculoServiceImpl(
            ManifiestoVehiculoRepository mvRepository,
            ManifiestoVehiculoMapper mvMapper,
            ManifiestoRepository manifiestoRepository,
            VehiculoRepository vehiculoRepository,
            ChoferRepository choferRepository,
            AyudanteRepository ayudanteRepository,
            RegistroLlegadaChoferService registroService // ⬅️ CONSTRUCTOR
    ) {
        this.mvRepository = mvRepository;
        this.mvMapper = mvMapper;
        this.manifiestoRepository = manifiestoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.choferRepository = choferRepository;
        this.ayudanteRepository = ayudanteRepository;
        this.registroService = registroService; // ⬅️ ASIGNACIÓN
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public ManifiestoVehiculo save(ManifiestoVehiculo entity) { return mvRepository.save(entity); }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManifiestoVehiculo> findById(Long id) { return mvRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoVehiculo> findAll() { return mvRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!mvRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Asignación con ID " + id + " no encontrada.");
        }
        mvRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<ManifiestoVehiculoDTO> getManifiestoVehiculoDTO(Long id) {
        return mvRepository.findById(id).map(mvMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoVehiculoDTO> getAllManifiestoVehiculos() {
        return mvRepository.findAll().stream().map(mvMapper::toDTO).collect(Collectors.toList());
    }

    // --- 1. MÉTODO DE REGISTRO/ASIGNACIÓN ---

    @Override
    @Transactional
    public ManifiestoVehiculoDTO register(ManifiestoVehiculoRegisterRequestDTO requestDTO) {

        // 1. Validar y Obtener Entidades FK
        Manifiesto manifiesto = manifiestoRepository.findById(requestDTO.getIdManifiesto())
                .orElseThrow(() -> new IllegalArgumentException("Manifiesto con ID " + requestDTO.getIdManifiesto() + " no encontrado."));

        Vehiculo vehiculo = vehiculoRepository.findById(requestDTO.getIdVehiculo())
                .orElseThrow(() -> new IllegalArgumentException("Vehículo con ID " + requestDTO.getIdVehiculo() + " no encontrado."));

        // 2. Obtener Entidades Opcionales (Chofer y Ayudante)
        Chofer chofer = requestDTO.getIdChofer() != null ? choferRepository.findById(requestDTO.getIdChofer())
                .orElseThrow(() -> new IllegalArgumentException("Chofer con ID " + requestDTO.getIdChofer() + " no encontrado.")) : null;

        Ayudante ayudante = requestDTO.getIdAyudante() != null ? ayudanteRepository.findById(requestDTO.getIdAyudante())
                .orElseThrow(() -> new IllegalArgumentException("Ayudante con ID " + requestDTO.getIdAyudante() + " no encontrado.")) : null;

        // 3. Mapear DTO a la entidad base
        ManifiestoVehiculo mv = mvMapper.toEntity(requestDTO);

        // 4. Asignar las referencias y campos automáticos
        mv.setManifiesto(manifiesto);
        mv.setVehiculo(vehiculo);
        mv.setChofer(chofer);
        mv.setAyudante(ayudante);
        mv.setEstado(requestDTO.getEstado() != null ? requestDTO.getEstado() : "ASIGNADO");

        // 5. Guardar la entidad ManifiestoVehiculo
        ManifiestoVehiculo savedMv = mvRepository.save(mv);

        // 🚀 PASO CLAVE: CREAR EL REGISTRO DE LLEGADA INICIAL (ESTADO NO_INICIADO)
        // Esto asegura que el estado de asistencia comience con el bloqueo.
        registroService.createInitialRegistro(savedMv);

        // 6. Retornar DTO de la asignación
        return mvMapper.toDTO(savedMv);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoVehiculoDTO> findByManifiestoId(Long idManifiesto) {
        return mvRepository.findByManifiesto_Id(idManifiesto).stream()
                .map(mvMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ManifiestoVehiculoDTO> findByVehiculoId(Long idVehiculo) {
        return mvRepository.findByVehiculo_Id(idVehiculo).stream()
                .map(mvMapper::toDTO)
                .collect(Collectors.toList());
    }
}