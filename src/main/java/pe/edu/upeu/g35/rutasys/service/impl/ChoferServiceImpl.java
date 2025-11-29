package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.dto.ChoferDTO;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;

import pe.edu.upeu.g35.rutasys.service.service.ChoferService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChoferServiceImpl implements ChoferService {

    private final ChoferRepository choferRepository;
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public ChoferServiceImpl(ChoferRepository choferRepository, UsuarioRepository usuarioRepository) {
        this.choferRepository = choferRepository;
        this.usuarioRepository = usuarioRepository;
    }

    // --- C: CREAR / ACTUALIZAR ---

    @Override
    public ChoferDTO registerChofer(Chofer chofer) {
        if (chofer.getUsuario() == null || chofer.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El Chofer debe estar asociado a un ID de Usuario válido.");
        }

        Long usuarioId = chofer.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new IllegalArgumentException("El Usuario con ID " + usuarioId + " no existe."));

        chofer.setUsuario(usuario);

        if (chofer.getEstado() == null || chofer.getEstado().isEmpty()) {
            chofer.setEstado("ACTIVO");
        }

        Chofer nuevoChofer = choferRepository.save(chofer);
        return convertToDto(nuevoChofer);
    }

    @Override
    public Chofer save(Chofer chofer) {
        return choferRepository.save(chofer);
    }


    // --- R: LEER / BÚSQUEDA ---

    @Override // ⬅️ MÉTODO AÑADIDO PARA RESOLVER EL ERROR
    public List<Chofer> findAll() {
        // Devuelve la lista de ENTIDADES, cumpliendo con el contrato de GenericService<T, ID>
        return choferRepository.findAll();
    }

    @Override
    public Optional<ChoferDTO> findByDni(String dni) {
        return choferRepository.findByDni(dni)
                .map(this::convertToDto);
    }

    @Override
    public ChoferDTO getChofer(Long id) {
        return choferRepository.findById(id)
                .map(this::convertToDto)
                .orElseThrow(() -> new RuntimeException("Chofer con ID " + id + " no encontrado."));
    }

    @Override
    public List<ChoferDTO> getAllChoferes() {
        // Usa el método findAll() del Repositorio y convierte a DTO
        return choferRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Chofer> findById(Long id) {
        return choferRepository.findById(id);
    }


    // --- D: ELIMINAR ---

    @Override
    public void delete(Long id) {
        if (!choferRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Chofer con ID " + id + " no encontrado.");
        }
        choferRepository.deleteById(id);
    }


    // --- MÉTODO DE CONVERSIÓN (HELPER) ---

    private ChoferDTO convertToDto(Chofer chofer) {
        ChoferDTO dto = new ChoferDTO();
        dto.setId(chofer.getId());
        dto.setNombreCompleto(chofer.getNombreCompleto());
        dto.setDni(chofer.getDni());
        dto.setLicencia(chofer.getLicencia());
        dto.setTelefono(chofer.getTelefono());
        dto.setCorreo(chofer.getCorreo());
        dto.setEstado(chofer.getEstado());

        if (chofer.getUsuario() != null) {
            dto.setUsuarioId(chofer.getUsuario().getId());
            dto.setUsername(chofer.getUsuario().getUsername());
        }
        return dto;
    }
}