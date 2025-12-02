package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.VehiculoDTO;
import pe.edu.upeu.g35.rutasys.dto.VehiculoRegisterRequestDTO;
import pe.edu.upeu.g35.rutasys.entity.Vehiculo;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface VehiculoMapper extends BaseMappers<Vehiculo, VehiculoDTO> { // ⬅️ Usa VehiculoDTO para CRUD general

    // --- Mapeo Entidad (Vehiculo) a DTO (VehiculoDTO) ---
    @Override
    @Mapping(target = "almacenBaseId", source = "almacenBase.id")
    @Mapping(target = "almacenBaseNombre", source = "almacenBase.nombre")
    @Mapping(target = "almacenBaseDireccion", source = "almacenBase.direccion")
    VehiculoDTO toDTO(Vehiculo vehiculo);

    // 🚀 CORRECCIÓN: Método específico para convertir el DTO de Registro (Request) a la Entidad
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "estado", ignore = true)
    @Mapping(target = "almacenBase", ignore = true) // El servicio se encarga de buscar la entidad AlmacenBase
    Vehiculo toEntity(VehiculoRegisterRequestDTO requestDTO); // ⬅️ Sobrecarga el método para aceptar el DTO de Registro

    // --- Mapeo DTO (VehiculoDTO) a Entidad (Vehiculo) ---
    @Override
    @InheritInverseConfiguration
    @Mapping(target = "almacenBase", ignore = true)
    Vehiculo toEntity(VehiculoDTO vehiculoDTO); // ⬅️ Usa el DTO de Presentación/Actualización
}