package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.RecepcionTienda;
import pe.edu.upeu.g35.rutasys.entity.ProgPreliminarDetalle;
import pe.edu.upeu.g35.rutasys.dto.RecepcionTiendaDTO;
import pe.edu.upeu.g35.rutasys.repository.RecepcionTiendaRepository;
import pe.edu.upeu.g35.rutasys.repository.ProgPreliminarDetalleRepository;
import pe.edu.upeu.g35.rutasys.mappers.RecepcionTiendaMapper;
import pe.edu.upeu.g35.rutasys.service.service.RecepcionTiendaService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RecepcionTiendaServiceImpl implements RecepcionTiendaService {

    private final RecepcionTiendaRepository recepcionRepository;
    private final ProgPreliminarDetalleRepository detalleRepository;
    private final RecepcionTiendaMapper recepcionMapper;

    public RecepcionTiendaServiceImpl(RecepcionTiendaRepository recepcionRepository, ProgPreliminarDetalleRepository detalleRepository, RecepcionTiendaMapper recepcionMapper) {
        this.recepcionRepository = recepcionRepository;
        this.detalleRepository = detalleRepository;
        this.recepcionMapper = recepcionMapper;
    }

    // --- LÓGICA CLAVE DE REGISTRO ---

    @Override
    @Transactional
    public RecepcionTiendaDTO registrarRecepcion(RecepcionTienda recepcionTienda) {

        // 1. Validar dependencia (ProgPreliminarDetalle)
        ProgPreliminarDetalle detalle = detalleRepository.findById(recepcionTienda.getProgPreliminarDetalle().getId())
                .orElseThrow(() -> new RuntimeException("Detalle de programación no encontrado."));

        // 2. Asignar datos de finalización
        recepcionTienda.setProgPreliminarDetalle(detalle);
        if (recepcionTienda.getFechaHoraRecepcion() == null) {
            recepcionTienda.setFechaHoraRecepcion(LocalDateTime.now());
        }

        // 3. Actualizar estado de la parada (ProgPreliminarDetalle)
        detalle.setEstado("ENTREGADO"); // Asumimos que el registro de recepción marca la entrega
        detalleRepository.save(detalle);

        // 4. Guardar Recepción
        RecepcionTienda saved = recepcionRepository.save(recepcionTienda);
        return recepcionMapper.toDTO(saved);
    }

    // [Se añaden los demás métodos CRUD heredados...]

    @Override
    public Optional<RecepcionTiendaDTO> getRecepcionDTO(Long id) { return recepcionRepository.findById(id).map(recepcionMapper::toDTO); }
    @Override
    public Optional<RecepcionTiendaDTO> getRecepcionByDetalleId(Long progPreliminarDetalleId) { return recepcionRepository.findByProgPreliminarDetalleId(progPreliminarDetalleId).map(recepcionMapper::toDTO); }
    @Override
    public RecepcionTienda save(RecepcionTienda recepcionTienda) { return recepcionRepository.save(recepcionTienda); }
    @Override
    public Optional<RecepcionTienda> findById(Long id) { return recepcionRepository.findById(id); }
    @Override
    public List<RecepcionTienda> findAll() { return recepcionRepository.findAll(); }
    @Override
    public void delete(Long id) { recepcionRepository.deleteById(id); }
}