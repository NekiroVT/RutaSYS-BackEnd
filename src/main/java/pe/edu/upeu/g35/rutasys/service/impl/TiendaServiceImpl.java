package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.Tienda;
import pe.edu.upeu.g35.rutasys.dto.TiendaDTO;
import pe.edu.upeu.g35.rutasys.repository.TiendaRepository;
import pe.edu.upeu.g35.rutasys.mappers.TiendaMapper;
import pe.edu.upeu.g35.rutasys.service.service.TiendaService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TiendaServiceImpl implements TiendaService {

    private final TiendaRepository tiendaRepository;
    private final TiendaMapper tiendaMapper;

    public TiendaServiceImpl(TiendaRepository tiendaRepository, TiendaMapper tiendaMapper) {
        this.tiendaRepository = tiendaRepository;
        this.tiendaMapper = tiendaMapper;
    }

    // --- MÉTODOS CRUD y DTO ---

    @Override
    public Tienda save(Tienda tienda) { return tiendaRepository.save(tienda); }
    @Override
    public Optional<Tienda> findById(Long id) { return tiendaRepository.findById(id); }
    @Override
    public List<Tienda> findAll() { return tiendaRepository.findAll(); }
    @Override
    public void delete(Long id) {
        if (!tiendaRepository.existsById(id)) {
            throw new RuntimeException("Tienda con ID " + id + " no encontrada.");
        }
        tiendaRepository.deleteById(id);
    }

    @Override
    public Optional<TiendaDTO> getTiendaDTO(Long id) {
        return tiendaRepository.findById(id).map(tiendaMapper::toDTO);
    }
    @Override
    public List<TiendaDTO> getAllTiendas() {
        return tiendaRepository.findAll().stream().map(tiendaMapper::toDTO).collect(Collectors.toList());
    }
    @Override
    public Optional<TiendaDTO> findByNombreTiendaDTO(String nombreTienda) {
        return tiendaRepository.findByNombreTienda(nombreTienda).map(tiendaMapper::toDTO);
    }
}