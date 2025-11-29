package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Ayudante;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.dto.AyudanteDTO;
import pe.edu.upeu.g35.rutasys.repository.AyudanteRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository; // Necesario para la dependencia
import pe.edu.upeu.g35.rutasys.mappers.AyudanteMapper;
import pe.edu.upeu.g35.rutasys.service.service.AyudanteService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AyudanteServiceImpl implements AyudanteService {

    private final AyudanteRepository ayudanteRepository;
    private final UsuarioRepository usuarioRepository;
    private final AyudanteMapper ayudanteMapper;

    public AyudanteServiceImpl(AyudanteRepository ayudanteRepository, UsuarioRepository usuarioRepository, AyudanteMapper ayudanteMapper) {
        this.ayudanteRepository = ayudanteRepository;
        this.usuarioRepository = usuarioRepository;
        this.ayudanteMapper = ayudanteMapper;
    }

    // --- MÉTODOS DE NEGOCIO Y CRUD ESPECÍFICO ---

    @Override
    public AyudanteDTO saveAyudante(Ayudante ayudante) {

        // 1. Validar Usuario (similar a Chofer)
        if (ayudante.getUsuario() == null || ayudante.getUsuario().getId() == null) {
            throw new IllegalArgumentException("El Ayudante debe estar asociado a un Usuario válido.");
        }

        Long usuarioId = ayudante.getUsuario().getId();
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario con ID " + usuarioId + " no encontrado."));

        // 2. Asignar el objeto Usuario gestionado
        ayudante.setUsuario(usuario);

        // 3. Guardar y mapear a DTO
        Ayudante saved = ayudanteRepository.save(ayudante);
        return ayudanteMapper.toDTO(saved);
    }

    // [Se añaden los demás métodos CRUD siguiendo el patrón de los otros servicios]

    @Override
    public Optional<AyudanteDTO> getAyudanteDTO(Long id) { return ayudanteRepository.findById(id).map(ayudanteMapper::toDTO); }
    @Override
    public List<AyudanteDTO> getAllAyudantes() { return ayudanteRepository.findAll().stream().map(ayudanteMapper::toDTO).collect(Collectors.toList()); }
    @Override
    public Optional<AyudanteDTO> findByDniDTO(String dni) { return ayudanteRepository.findByDni(dni).map(ayudanteMapper::toDTO); }
    @Override
    public Ayudante save(Ayudante ayudante) { return ayudanteRepository.save(ayudante); }
    @Override
    public Optional<Ayudante> findById(Long id) { return ayudanteRepository.findById(id); }
    @Override
    public List<Ayudante> findAll() { return ayudanteRepository.findAll(); }
    @Override
    public void delete(Long id) { ayudanteRepository.deleteById(id); }
}