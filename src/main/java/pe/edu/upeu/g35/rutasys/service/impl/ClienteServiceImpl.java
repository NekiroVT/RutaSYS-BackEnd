package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.repository.ClienteRepository;
import pe.edu.upeu.g35.rutasys.mappers.ClienteMapper;
import pe.edu.upeu.g35.rutasys.service.service.ClienteService;
import org.springframework.stereotype.Service;
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

    // --- MÉTODOS CRUD y DTO ---

    @Override
    public Cliente save(Cliente cliente) { return clienteRepository.save(cliente); }
    @Override
    public Optional<Cliente> findById(Long id) { return clienteRepository.findById(id); }
    @Override
    public List<Cliente> findAll() { return clienteRepository.findAll(); }
    @Override
    public void delete(Long id) { clienteRepository.deleteById(id); }

    @Override
    public Optional<ClienteDTO> getClienteDTO(Long id) { return clienteRepository.findById(id).map(clienteMapper::toDTO); }
    @Override
    public List<ClienteDTO> getAllClientes() { return clienteRepository.findAll().stream().map(clienteMapper::toDTO).collect(Collectors.toList()); }
    @Override
    public Optional<ClienteDTO> findByRucDTO(String ruc) { return clienteRepository.findByRuc(ruc).map(clienteMapper::toDTO); }
}