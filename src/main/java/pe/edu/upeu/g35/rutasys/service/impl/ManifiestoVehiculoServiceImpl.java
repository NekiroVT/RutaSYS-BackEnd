package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.*;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoVehiculoDTO;
import pe.edu.upeu.g35.rutasys.repository.*;
import pe.edu.upeu.g35.rutasys.mappers.ManifiestoVehiculoMapper;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoVehiculoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManifiestoVehiculoServiceImpl implements ManifiestoVehiculoService {

    private final ManifiestoVehiculoRepository mvRepository;
    private final ManifiestoRepository manifiestoRepository;
    private final VehiculoRepository vehiculoRepository;
    private final ChoferRepository choferRepository;
    private final AyudanteRepository ayudanteRepository; // Asumo que AyudanteRepository existe
    private final ManifiestoVehiculoMapper mvMapper;

    public ManifiestoVehiculoServiceImpl(ManifiestoVehiculoRepository mvRepository, ManifiestoRepository manifiestoRepository,
                                         VehiculoRepository vehiculoRepository, ChoferRepository choferRepository,
                                         AyudanteRepository ayudanteRepository, ManifiestoVehiculoMapper mvMapper) {
        this.mvRepository = mvRepository;
        this.manifiestoRepository = manifiestoRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.choferRepository = choferRepository;
        this.ayudanteRepository = ayudanteRepository;
        this.mvMapper = mvMapper;
    }

    // --- LÓGICA CLAVE DE ASIGNACIÓN ---

    @Override
    public ManifiestoVehiculoDTO assignResourcesToManifest(ManifiestoVehiculoDTO assignmentDTO) {

        // 1. Validar que el Manifiesto y el Vehículo existan (Obligatorios)
        Manifiesto manifiesto = manifiestoRepository.findById(assignmentDTO.getManifiestoId())
                .orElseThrow(() -> new RuntimeException("Manifiesto no encontrado."));
        Vehiculo vehiculo = vehiculoRepository.findById(assignmentDTO.getVehiculoId())
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado."));

        // 2. Mapear y buscar las entidades opcionales (Chofer y Ayudante)
        ManifiestoVehiculo mv = mvMapper.toEntity(assignmentDTO);

        if (assignmentDTO.getChoferId() != null) {
            Chofer chofer = choferRepository.findById(assignmentDTO.getChoferId())
                    .orElseThrow(() -> new RuntimeException("Chofer no encontrado."));
            mv.setChofer(chofer);
        }

        if (assignmentDTO.getAyudanteId() != null) {
            Ayudante ayudante = ayudanteRepository.findById(assignmentDTO.getAyudanteId())
                    .orElseThrow(() -> new RuntimeException("Ayudante no encontrado."));
            mv.setAyudante(ayudante);
        }

        // 3. Asignar entidades principales
        mv.setManifiesto(manifiesto);
        mv.setVehiculo(vehiculo);
        mv.setEstado("ASIGNADO");

        // 4. Guardar y retornar
        ManifiestoVehiculo saved = mvRepository.save(mv);
        return mvMapper.toDTO(saved);
    }

    @Override
    public List<ManifiestoVehiculoDTO> getAssignmentsByManifestId(Long manifiestoId) {
        return mvRepository.findByManifiestoId(manifiestoId).stream()
                .map(mvMapper::toDTO)
                .collect(Collectors.toList());
    }

    // [Se añaden los demás métodos CRUD heredados...]

    @Override
    public ManifiestoVehiculo save(ManifiestoVehiculo mv) { return mvRepository.save(mv); }
    @Override
    public Optional<ManifiestoVehiculoDTO> getManifiestoVehiculoDTO(Long id) { return mvRepository.findById(id).map(mvMapper::toDTO); }
    @Override
    public Optional<ManifiestoVehiculo> findById(Long id) { return mvRepository.findById(id); }
    @Override
    public List<ManifiestoVehiculo> findAll() { return mvRepository.findAll(); }
    @Override
    public void delete(Long id) { mvRepository.deleteById(id); }
}