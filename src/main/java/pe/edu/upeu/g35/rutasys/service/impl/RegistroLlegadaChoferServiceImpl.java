package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.*;
import pe.edu.upeu.g35.rutasys.dto.RegistroLlegadaChoferDTO;
import pe.edu.upeu.g35.rutasys.repository.*;
import pe.edu.upeu.g35.rutasys.mappers.RegistroLlegadaChoferMapper;
import pe.edu.upeu.g35.rutasys.service.service.RegistroLlegadaChoferService;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroLlegadaChoferServiceImpl implements RegistroLlegadaChoferService {

    private final RegistroLlegadaChoferRepository registroRepository;
    private final ManifiestoVehiculoRepository mvRepository;
    private final ChoferRepository choferRepository;
    private final RegistroLlegadaChoferMapper registroMapper;

    public RegistroLlegadaChoferServiceImpl(RegistroLlegadaChoferRepository registroRepository, ManifiestoVehiculoRepository mvRepository, ChoferRepository choferRepository, RegistroLlegadaChoferMapper registroMapper) {
        this.registroRepository = registroRepository;
        this.mvRepository = mvRepository;
        this.choferRepository = choferRepository;
        this.registroMapper = registroMapper;
    }

    // --- LÓGICA CLAVE DE REGISTRO DE LLEGADA ---

    @Override
    public RegistroLlegadaChoferDTO registrarLlegada(RegistroLlegadaChofer registro) {

        // 1. Validar ManifiestoVehiculo y Chofer (Dependencias)
        ManifiestoVehiculo mv = mvRepository.findById(registro.getManifiestoVehiculo().getId())
                .orElseThrow(() -> new RuntimeException("Asignación de vehículo/manifiesto no encontrada."));
        Chofer chofer = choferRepository.findById(registro.getChofer().getId())
                .orElseThrow(() -> new RuntimeException("Chofer no encontrado."));

        // 2. Asignar entidades gestionadas y valores por defecto
        registro.setManifiestoVehiculo(mv);
        registro.setChofer(chofer);
        if (registro.getFechaHoraLlegada() == null) {
            registro.setFechaHoraLlegada(LocalDateTime.now());
        }
        if (registro.getEstadoLlegada() == null) {
            registro.setEstadoLlegada("REGISTRADO");
        }

        // 3. Guardar y retornar
        RegistroLlegadaChofer saved = registroRepository.save(registro);
        return registroMapper.toDTO(saved);
    }

    // [Se añaden los demás métodos CRUD heredados...]

    @Override public Optional<RegistroLlegadaChoferDTO> getRegistroDTO(Long id) { return registroRepository.findById(id).map(registroMapper::toDTO); }
    @Override public Optional<RegistroLlegadaChoferDTO> getRegistroByManifiestoVehiculoId(Long manifiestoVehiculoId) { return registroRepository.findByManifiestoVehiculoId(manifiestoVehiculoId).map(registroMapper::toDTO); }
    @Override public RegistroLlegadaChofer save(RegistroLlegadaChofer registro) { return registroRepository.save(registro); }
    @Override public Optional<RegistroLlegadaChofer> findById(Long id) { return registroRepository.findById(id); }
    @Override public List<RegistroLlegadaChofer> findAll() { return registroRepository.findAll(); }
    @Override public void delete(Long id) {
        if (!registroRepository.existsById(id)) throw new RuntimeException("Registro no encontrado.");
        registroRepository.deleteById(id);
    }
}