package pe.edu.upeu.g35.rutasys.service.service;

import pe.edu.upeu.g35.rutasys.entity.Cliente;
import pe.edu.upeu.g35.rutasys.dto.ClienteDTO;
import pe.edu.upeu.g35.rutasys.service.base.GenericService;

import java.util.List;
import java.util.Optional;

public interface ClienteService extends GenericService<Cliente, Long> {

    Optional<ClienteDTO> getClienteDTO(Long id);

    List<ClienteDTO> getAllClientes();

    Optional<ClienteDTO> findByRucDTO(String ruc);
}