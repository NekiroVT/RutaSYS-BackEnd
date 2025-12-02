package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.RegistroLlegadaChofer;
import pe.edu.upeu.g35.rutasys.entity.ManifiestoVehiculo;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.dto.AsistenciaEstadoDTO; // ⬅️ Necesario para el nuevo método
import pe.edu.upeu.g35.rutasys.repository.RegistroLlegadaChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.ManifiestoVehiculoRepository;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.mappers.RegistroLlegadaChoferMapper;
import pe.edu.upeu.g35.rutasys.service.service.RegistroLlegadaChoferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RegistroLlegadaChoferServiceImpl implements RegistroLlegadaChoferService {

    private final RegistroLlegadaChoferRepository registroRepository;
    private final RegistroLlegadaChoferMapper registroMapper;
    private final ManifiestoVehiculoRepository mvRepository;
    private final ChoferRepository choferRepository;

    private static final String ESTADO_NO_INICIADO = "NO_INICIADO"; // Bloqueado, esperando habilitación
    private static final String ESTADO_ACTIVO = "ACTIVO";         // Habilitado para tomar asistencia
    private static final String ESTADO_PRESENTE = "PRESENTE";     // Asistencia tomada (Finalizado con éxito)
    private static final String ESTADO_FALTO = "FALTO";           // El chofer faltó (Finalizado con fallo)
    private static final String ESTADO_NO_ASIGNADO = "NO_ASIGNADO"; // Sin registros RLC

    public RegistroLlegadaChoferServiceImpl(RegistroLlegadaChoferRepository registroRepository, RegistroLlegadaChoferMapper registroMapper, ManifiestoVehiculoRepository mvRepository, ChoferRepository choferRepository) {
        this.registroRepository = registroRepository;
        this.registroMapper = registroMapper;
        this.mvRepository = mvRepository;
        this.choferRepository = choferRepository;
    }

    // --- MÉTODOS CRUD y DTO de Presentación (sin cambios) ---

    @Override @Transactional public RegistroLlegadaChofer save(RegistroLlegadaChofer entity) { return registroRepository.save(entity); }
    @Override @Transactional(readOnly = true) public Optional<RegistroLlegadaChofer> findById(Long id) { return registroRepository.findById(id); }
    @Override @Transactional(readOnly = true) public List<RegistroLlegadaChofer> findAll() { return registroRepository.findAll(); }
    @Override @Transactional public void delete(Long id) {
        if (!registroRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Registro de Llegada con ID " + id + " no encontrado.");
        }
        registroRepository.deleteById(id);
    }
    @Override @Transactional(readOnly = true) public Optional<RegistroLlegadaChoferDTO> getRegistroLlegadaChoferDTO(Long id) { return registroRepository.findById(id).map(registroMapper::toDTO); }
    @Override @Transactional(readOnly = true) public List<RegistroLlegadaChoferDTO> getAllRegistros() { return registroRepository.findAll().stream().map(registroMapper::toDTO).collect(Collectors.toList()); }
    @Override @Transactional(readOnly = true) public Optional<RegistroLlegadaChoferDTO> findByManifiestoVehiculoIdDTO(Long idMv) { return registroRepository.findByManifiestoVehiculo_Id(idMv).map(registroMapper::toDTO); }
    @Override @Transactional(readOnly = true) public List<RegistroLlegadaChoferDTO> findByChoferIdDTO(Long idChofer) { return registroRepository.findByChofer_Id(idChofer).stream().map(registroMapper::toDTO).collect(Collectors.toList()); }


    // 🚀 MÉTODO CLAVE: Crea los registros iniciales (NO_INICIADO)
    @Transactional
    public RegistroLlegadaChoferDTO createInitialRegistro(ManifiestoVehiculo mv) {

        // 1. Validar unicidad (evita que se cree un registro doble para el mismo M-V)
        if (registroRepository.findByManifiestoVehiculo_Id(mv.getId()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un registro de llegada para la asignación M-V con ID: " + mv.getId());
        }

        RegistroLlegadaChofer registro = RegistroLlegadaChofer.builder()
                .manifiestoVehiculo(mv)
                .chofer(mv.getChofer())
                .fechaHoraLlegada(LocalDateTime.now()) // Usar hora actual para la creación (placeholder)
                .ubicacionTexto("CREACIÓN INICIAL")
                .estadoLlegada(ESTADO_NO_INICIADO) // ⬅️ Estado de bloqueo
                .build();

        RegistroLlegadaChofer savedRegistro = registroRepository.save(registro);
        return registroMapper.toDTO(savedRegistro);
    }

    // 🚀 MÉTODO CLAVE: Dispara la asistencia real para el grupo (Actualización Masiva)
    @Override
    @Transactional
    public RegistroLlegadaChoferDTO register(RegistroLlegadaChoferRegisterRequestDTO requestDTO) {

        Long idMvDisparador = requestDTO.getIdManifiestoVehiculo();

        // 1. Obtener la asignación Manifiesto-Vehículo (MV) que disparó la acción
        ManifiestoVehiculo mvDisparador = mvRepository.findById(idMvDisparador)
                .orElseThrow(() -> new IllegalArgumentException("Asignación Manifiesto-Vehículo con ID " + idMvDisparador + " no encontrada."));

        Long idVehiculo = mvDisparador.getVehiculo().getId();

        // 2. Buscar TODOS los registros de llegada que comparten el mismo vehículo y están en estado ACTIVO
        List<RegistroLlegadaChofer> registrosParaActualizar =
                registroRepository.findByVehiculoIdAndEstado(idVehiculo, ESTADO_ACTIVO);

        if (registrosParaActualizar.isEmpty()) {
            throw new IllegalArgumentException("El vehículo ID " + idVehiculo + " no tiene asistencias pendientes (estado ACTIVO) para registrar su llegada.");
        }

        // 3. Registrar la llegada y actualizar el estado de TODOS los registros a PRESENTE
        RegistroLlegadaChofer registroDisparador = null;

        for (RegistroLlegadaChofer registro : registrosParaActualizar) {

            // 3.1. Actualizar campos de la entidad
            registro.setFechaHoraLlegada(LocalDateTime.now());
            registro.setUbicacionTexto(requestDTO.getUbicacionTexto());
            registro.setLatitud(requestDTO.getLatitud());
            registro.setLongitud(requestDTO.getLongitud());
            registro.setFotoEvidencia(requestDTO.getFotoEvidencia());
            registro.setEstadoLlegada(ESTADO_PRESENTE); // ⬅️ Estado final

            // 3.2. Guardar el registro actualizado
            RegistroLlegadaChofer savedRegistro = registroRepository.save(registro);

            // 3.3. Identificar el registro que se mapea al DTO de respuesta
            if (savedRegistro.getManifiestoVehiculo().getId().equals(idMvDisparador)) {
                registroDisparador = savedRegistro;
            }
        }

        if (registroDisparador == null) {
            throw new RuntimeException("Error fatal: El registro disparador no fue encontrado en el set de actualización.");
        }

        // 4. Retornar el DTO de la instancia que disparó la acción
        return registroMapper.toDTO(registroDisparador);
    }

    // 🚀 IMPLEMENTACIÓN FALTANTE: Consulta de estado dominante para el frontend
    @Override
    @Transactional(readOnly = true)
    public Optional<AsistenciaEstadoDTO> getEstadoAsistenciaDominante(Long idChofer) {
        // 1. Verificar si el Chofer existe y obtener su información
        Chofer chofer = choferRepository.findById(idChofer)
                .orElseThrow(() -> new IllegalArgumentException("Chofer con ID " + idChofer + " no encontrado."));

        // 2. Buscar TODOS los registros RLC asociados a este Chofer
        // NOTA: Para obtener el estado dominante, solo necesitamos buscar si hay registros
        // en PRESENTE, ACTIVO, NO_INICIADO o FALTO.
        List<RegistroLlegadaChofer> todosRegistrosChofer = registroRepository.findByChofer_Id(idChofer);

        if (todosRegistrosChofer.isEmpty()) {
            return Optional.empty(); // No tiene asignaciones RLC de ningún tipo
        }

        // Asumimos que todos los registros de RLC para un chofer, en un momento dado,
        // están asociados al MISMO vehículo (basado en la lógica de asignación).
        Long idVehiculo = todosRegistrosChofer.get(0).getManifiestoVehiculo().getVehiculo().getId();

        // 3. Determinar el estado dominante (PRESENTE > ACTIVO > FALTO > NO_INICIADO)

        String estadoDominante = ESTADO_NO_ASIGNADO;
        Long idMvDisparador = null;

        boolean isPresente = false;
        boolean isActive = false;

        for (RegistroLlegadaChofer registro : todosRegistrosChofer) {
            String estado = registro.getEstadoLlegada();

            if (ESTADO_PRESENTE.equals(estado)) {
                isPresente = true; // El estado final PRESENTE domina sobre todos los demás.
                estadoDominante = ESTADO_PRESENTE;
                break; // Si uno es PRESENTE, todos los demás deberían serlo, salimos.
            }

            if (ESTADO_ACTIVO.equals(estado)) {
                isActive = true; // El estado ACTIVO es el que nos interesa para el registro.
                if (idMvDisparador == null) {
                    idMvDisparador = registro.getManifiestoVehiculo().getId(); // Guarda el primer MV activo como disparador
                }
            }

            if (ESTADO_FALTO.equals(estado)) {
                if (!isPresente && !isActive) {
                    estadoDominante = ESTADO_FALTO; // Si no hay PRESENTE o ACTIVO, FALTO domina
                }
            }

            if (ESTADO_NO_INICIADO.equals(estado)) {
                if (!isPresente && !isActive && !ESTADO_FALTO.equals(estadoDominante)) {
                    estadoDominante = ESTADO_NO_INICIADO; // NO_INICIADO es el estado menos prioritario
                }
            }
        }

        // Si encontramos ACTIVO, ese es el estado a devolver (tiene prioridad sobre NO_INICIADO y FALTO).
        if (isActive && !isPresente) {
            estadoDominante = ESTADO_ACTIVO;
        }

        // 4. Construir y devolver el DTO de Estado
        AsistenciaEstadoDTO result = AsistenciaEstadoDTO.builder()
                .estadoDominante(estadoDominante)
                .idManifiestoVehiculoDisparador(idMvDisparador)
                .idVehiculoAsignado(idVehiculo)
                .idChofer(idChofer)
                .choferNombreCompleto(chofer.getNombreCompleto())
                .build();

        return Optional.of(result);
    }

    // 🚀 CORRECCIÓN: Habilita la asistencia (Ahora afecta a TODOS los RLC de ese vehículo)
    @Override
    @Transactional
    public void habilitarAsistencia(Long idManifiestoVehiculo) {
        RegistroLlegadaChofer registroDisparador = registroRepository.findByManifiestoVehiculo_Id(idManifiestoVehiculo)
                .orElseThrow(() -> new IllegalArgumentException("Registro de llegada no encontrado para habilitación."));

        // 1. Validar que el disparador esté en el estado inicial correcto
        if (!ESTADO_NO_INICIADO.equals(registroDisparador.getEstadoLlegada())) {
            throw new IllegalArgumentException("La asistencia ya está en estado '" + registroDisparador.getEstadoLlegada() + "' y no puede ser habilitada.");
        }

        // 2. Obtener el ID del vehículo que se va a habilitar
        Long idVehiculo = registroDisparador.getManifiestoVehiculo().getVehiculo().getId();

        // 3. Buscar TODOS los registros para ese vehículo que estén en NO_INICIADO
        List<RegistroLlegadaChofer> registrosParaHabilitar =
                registroRepository.findByVehiculoIdAndEstado(idVehiculo, ESTADO_NO_INICIADO);

        // 4. Cambiar todos los NO_INICIADO a ACTIVO (Actualización Masiva)
        if (registrosParaHabilitar.isEmpty()) {
            // Este caso solo debería ocurrir si ya fueron habilitados o marcados como FALTO.
            throw new IllegalArgumentException("No hay registros pendientes (NO_INICIADO) para habilitar en el vehículo ID " + idVehiculo);
        }

        registrosParaHabilitar.forEach(r -> {
            r.setEstadoLlegada(ESTADO_ACTIVO);
            registroRepository.save(r);
        });
    }

    // 🚀 CORRECCIÓN: Marca todos los registros pendientes (ACTIVO o NO_INICIADO) de un vehículo como FALTO
    @Override
    @Transactional
    public List<RegistroLlegadaChoferDTO> marcarComoFaltoPorVehiculo(Long idVehiculo) {

        // 1. Buscar registros en estado ACTIVO y NO_INICIADO
        List<RegistroLlegadaChofer> registrosActivos =
                registroRepository.findByVehiculoIdAndEstado(idVehiculo, ESTADO_ACTIVO);

        List<RegistroLlegadaChofer> registrosNoIniciados =
                registroRepository.findByVehiculoIdAndEstado(idVehiculo, ESTADO_NO_INICIADO);

        // Combinar ambas listas
        List<RegistroLlegadaChofer> registrosParaActualizar = new java.util.ArrayList<>();
        registrosParaActualizar.addAll(registrosActivos);
        registrosParaActualizar.addAll(registrosNoIniciados);


        if (registrosParaActualizar.isEmpty()) {
            throw new IllegalArgumentException("No hay asistencias pendientes (ACTIVO o NO_INICIADO) para marcar como FALTO en el vehículo ID " + idVehiculo);
        }

        // 2. Actualizar a FALTO
        List<RegistroLlegadaChofer> resultados = registrosParaActualizar.stream().map(registro -> {
            registro.setEstadoLlegada(ESTADO_FALTO);
            return registroRepository.save(registro);
        }).collect(Collectors.toList());

        return registroMapper.toDTOs(resultados);
    }
}