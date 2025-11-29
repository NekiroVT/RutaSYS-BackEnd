package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO; // ⬅️ IMPORTACIÓN AÑADIDA
import pe.edu.upeu.g35.rutasys.repository.ManifiestoRepository;
import pe.edu.upeu.g35.rutasys.repository.ClienteRepository;
import pe.edu.upeu.g35.rutasys.mappers.ManifiestoMapper;
import pe.edu.upeu.g35.rutasys.service.service.ManifiestoService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ManifiestoServiceImpl implements ManifiestoService {

    private final ManifiestoRepository manifiestoRepository;
    private final ClienteRepository clienteRepository;
    private final ManifiestoMapper manifiestoMapper;

    public ManifiestoServiceImpl(ManifiestoRepository manifiestoRepository, ClienteRepository clienteRepository, ManifiestoMapper manifiestoMapper) {
        this.manifiestoRepository = manifiestoRepository;
        this.clienteRepository = clienteRepository;
        this.manifiestoMapper = manifiestoMapper;
    }

    // --- MÉTODOS DE NEGOCIO Y CRUD ESPECÍFICO ---

    @Override
    // ⬅️ FIRMA CORRECTA: Debe retornar ManifiestoDTO para coincidir con la interfaz
    public ManifiestoDTO saveManifiesto(Manifiesto manifiesto) {

        // 1. Validar Cliente
        if (manifiesto.getCliente() == null || manifiesto.getCliente().getId() == null) {
            throw new IllegalArgumentException("El Manifiesto debe estar asociado a un Cliente válido.");
        }

        Long clienteId = manifiesto.getCliente().getId();
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente con ID " + clienteId + " no encontrado."));

        // 2. Asignar el objeto Cliente gestionado
        manifiesto.setCliente(cliente);

        // 3. Guardar y mapear a DTO
        Manifiesto saved = manifiestoRepository.save(manifiesto);
        return manifiestoMapper.toDTO(saved);
    }

    @Override
    public List<ManifiestoDTO> getAllManifiestos() {
        return manifiestoRepository.findAll().stream()
                .map(manifiestoMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS HEREDADOS (CRUD Genérico) ---

    @Override
    public Manifiesto save(Manifiesto manifiesto) { return manifiestoRepository.save(manifiesto); }
    @Override
    public Optional<Manifiesto> findById(Long id) { return manifiestoRepository.findById(id); }
    @Override
    public List<Manifiesto> findAll() { return manifiestoRepository.findAll(); }
    @Override
    public void delete(Long id) {
        if (!manifiestoRepository.existsById(id)) {
            throw new RuntimeException("Manifiesto con ID " + id + " no encontrado.");
        }
        manifiestoRepository.deleteById(id);
    }
    @Override
    public Optional<ManifiestoDTO> getManifiestoDTO(Long id) {
        return manifiestoRepository.findById(id).map(manifiestoMapper::toDTO);
    }
}