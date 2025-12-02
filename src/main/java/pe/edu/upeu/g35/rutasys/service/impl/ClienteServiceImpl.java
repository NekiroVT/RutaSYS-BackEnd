package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.repository.ClienteRepository;
import pe.edu.upeu.g35.rutasys.mappers.ClienteMapper;
import pe.edu.upeu.g35.rutasys.service.service.ClienteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository clienteRepository;
    private final ClienteMapper clienteMapper;

    public ClienteServiceImpl(ClienteRepository clienteRepository, ClienteMapper clienteMapper) {
        this.clienteRepository = clienteRepository;
        this.clienteMapper = clienteMapper;
    }

    // --- MÉTODOS HEREDADOS DE GenericService (CRUD de Entidad) ---

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        // Este método es usado por GenericService para operaciones CRUD genéricas,
        // o por AuthService para guardar la entidad ya preparada.
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> findById(Long id) { return clienteRepository.findById(id); }

    @Override
    @Transactional(readOnly = true)
    public List<Cliente> findAll() { return clienteRepository.findAll(); }

    @Override
    @Transactional
    public void delete(Long id) {
        // Lógica mejorada: Verificar si el cliente existe antes de eliminar (similar a ChoferService)
        if (!clienteRepository.existsById(id)) {
            throw new RuntimeException("No se puede eliminar: Cliente con ID " + id + " no encontrado.");
        }
        clienteRepository.deleteById(id);
    }

    // --- MÉTODOS DTO de Presentación y Búsqueda Básica ---

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> getClienteDTO(Long id) {
        // Mapea la Entidad a ClienteDTO si existe.
        return clienteRepository.findById(id).map(clienteMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> getAllClientes() {
        // Obtiene todos y los mapea a una lista de DTOs.
        return clienteRepository.findAll().stream().map(clienteMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ClienteDTO> findByRucDTO(String ruc) {
        // Busca por RUC y mapea a DTO.
        return clienteRepository.findByRuc(ruc).map(clienteMapper::toDTO);
    }

    // --- 1. MÉTODO DE REGISTRO (Lógica de negocio simple, usado por AuthService) ---

    @Override
    @Transactional
    public ClienteDTO registerCliente(Cliente cliente) {
        // Se asume que la entidad Cliente ya fue validada y su estado fue establecido
        // por la capa de seguridad (AuthService) antes de llegar aquí.

        // Simplemente guarda y retorna el DTO del cliente recién guardado.
        Cliente savedCliente = clienteRepository.save(cliente);

        // Mapear la entidad guardada de vuelta al DTO para la respuesta
        return clienteMapper.toDTO(savedCliente);
    }

    // --- MÉTODOS DE BÚSQUEDA ADICIONALES ---

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findByRazonSocialContaining(String razonSocial) {
        // Busca por coincidencia de Razón Social (ignorando mayúsculas/minúsculas)
        return clienteRepository.findByRazonSocialContainingIgnoreCase(razonSocial).stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> findByEstadoDTO(String estado) {
        // Busca por estado y mapea los resultados a DTOs.
        return clienteRepository.findByEstado(estado).stream()
                .map(clienteMapper::toDTO)
                .collect(Collectors.toList());
    }
}