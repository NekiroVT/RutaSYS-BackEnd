package pe.edu.upeu.g35.rutasys.service.impl;

import pe.edu.upeu.g35.rutasys.entity.*;
import pe.edu.upeu.g35.rutasys.dto.ProgPreliminarDetalleDTO;
import pe.edu.upeu.g35.rutasys.dto.InfoStopViewDTO; // ⬅️ Nuevo DTO de la Vista
import pe.edu.upeu.g35.rutasys.repository.*;
import pe.edu.upeu.g35.rutasys.mappers.ProgPreliminarDetalleMapper;
import pe.edu.upeu.g35.rutasys.service.service.ProgPreliminarDetalleService;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgPreliminarDetalleServiceImpl implements ProgPreliminarDetalleService {

    private final ProgPreliminarDetalleRepository detalleRepository;
    private final ManifiestoVehiculoRepository mvRepository;
    private final TiendaRepository tiendaRepository;
    private final VehiculoRepository vehiculoRepository; // ⬅️ Necesario para el DTO de la Vista
    private final ProgPreliminarDetalleMapper detalleMapper;

    public ProgPreliminarDetalleServiceImpl(ProgPreliminarDetalleRepository detalleRepository, ManifiestoVehiculoRepository mvRepository, TiendaRepository tiendaRepository, VehiculoRepository vehiculoRepository, ProgPreliminarDetalleMapper detalleMapper) {
        this.detalleRepository = detalleRepository;
        this.mvRepository = mvRepository;
        this.tiendaRepository = tiendaRepository;
        this.vehiculoRepository = vehiculoRepository;
        this.detalleMapper = detalleMapper;
    }

    // --- MÉTODOS DE NEGOCIO Y CRUD ESPECÍFICO ---

    @Override
    public ProgPreliminarDetalleDTO saveDetalle(ProgPreliminarDetalle detalle) {

        // 1. Validar ManifiestoVehiculo y Tienda (Dependencias)
        ManifiestoVehiculo mv = mvRepository.findById(detalle.getManifiestoVehiculo().getId())
                .orElseThrow(() -> new RuntimeException("Asignación de vehículo/manifiesto no encontrada."));
        Tienda tienda = tiendaRepository.findById(detalle.getTienda().getId())
                .orElseThrow(() -> new RuntimeException("Tienda no encontrada."));

        // 2. Asignar entidades gestionadas
        detalle.setManifiestoVehiculo(mv);
        detalle.setTienda(tienda);

        // 3. Establecer estado inicial si es nuevo
        if (detalle.getId() == null && detalle.getEstado() == null) {
            detalle.setEstado("PENDIENTE");
        }

        // 4. Guardar y retornar
        ProgPreliminarDetalle saved = detalleRepository.save(detalle);
        return detalleMapper.toDTO(saved);
    }

    // ⬅️ MÉTODO DE LÓGICA DE VISTA (INFO BUTTON)
    @Override
    public Optional<InfoStopViewDTO> getStopDetailViewById(Long detalleId) {
        // En un proyecto real, este método usaría una consulta SELECT NEW o un ViewRepository.
        // Aquí simulamos la unión de datos desde la Entidad ProgPreliminarDetalle, asumiendo acceso a las FK.

        return detalleRepository.findById(detalleId)
                .flatMap(detalle -> {
                    // Asumiendo que el Vehiculo se puede obtener a través de la relación:
                    ManifiestoVehiculo mv = detalle.getManifiestoVehiculo();
                    Vehiculo vehiculo = mv.getVehiculo();
                    Tienda tienda = detalle.getTienda();

                    // Construcción del DTO de la vista (InfoStopViewDTO)
                    InfoStopViewDTO dto = new InfoStopViewDTO();

                    // Mapeo de Vehiculo (Placa)
                    dto.setPlaca(vehiculo.getPlaca());

                    // Mapeo de Tienda
                    dto.setNombreTienda(tienda.getNombreTienda());
                    dto.setDistritoTienda(tienda.getDistrito());
                    dto.setDireccionTienda(tienda.getDireccion());
                    dto.setCanal(tienda.getCanal());
                    dto.setDestinatario(tienda.getDestinatario());
                    dto.setVhInicio(tienda.getVhInicio());
                    dto.setVhFin(tienda.getVhFin());
                    dto.setNumeroPrincipal(tienda.getNumeroPrincipal());
                    dto.setNumeroOpcional(tienda.getNumeroOpcional());
                    dto.setCorreoTienda(tienda.getEmailTienda());

                    // Mapeo de Detalle de Pedido
                    dto.setDocumentacionRequerida(detalle.getDocumentacion());
                    dto.setPex(detalle.getPex());
                    dto.setVolumenM3(detalle.getVolumenM3());
                    dto.setEstado(detalle.getEstado());

                    return Optional.of(dto);
                });
    }


    @Override
    public List<ProgPreliminarDetalleDTO> getDetallesByManifiestoVehiculoId(Long manifiestoVehiculoId) {
        return detalleRepository.findByManifiestoVehiculoId(manifiestoVehiculoId).stream()
                .map(detalleMapper::toDTO)
                .collect(Collectors.toList());
    }

    // --- MÉTODOS HEREDADOS (CRUD Genérico) ---

    @Override
    public Optional<ProgPreliminarDetalleDTO> getDetalleDTO(Long id) { return detalleRepository.findById(id).map(detalleMapper::toDTO); }
    @Override
    public ProgPreliminarDetalle save(ProgPreliminarDetalle detalle) { return detalleRepository.save(detalle); }
    @Override
    public Optional<ProgPreliminarDetalle> findById(Long id) { return detalleRepository.findById(id); }
    @Override
    public List<ProgPreliminarDetalle> findAll() { return detalleRepository.findAll(); }
    @Override
    public void delete(Long id) { detalleRepository.deleteById(id); }
}