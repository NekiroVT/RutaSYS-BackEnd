package pe.edu.upeu.g35.rutasys.mappers;

import pe.edu.upeu.g35.rutasys.dto.ManifiestoDTO;
import pe.edu.upeu.g35.rutasys.entity.Manifiesto;
import pe.edu.upeu.g35.rutasys.mappers.base.BaseMappers;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.InheritInverseConfiguration;

@Mapper(componentModel = "spring")
public interface ManifiestoMapper extends BaseMappers<Manifiesto, ManifiestoDTO> {

    @Override
    @Mapping(target = "clienteId", source = "cliente.id")
    @Mapping(target = "razonSocialCliente", source = "cliente.razonSocial")
    ManifiestoDTO toDTO(Manifiesto manifiesto);

    @Override
    @InheritInverseConfiguration
    @Mapping(target = "cliente", ignore = true)
    Manifiesto toEntity(ManifiestoDTO manifiestoDTO);
}