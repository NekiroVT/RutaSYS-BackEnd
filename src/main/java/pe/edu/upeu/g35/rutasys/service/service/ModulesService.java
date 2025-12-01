package pe.edu.upeu.g35.rutasys.service;

import pe.edu.upeu.g35.rutasys.dto.ModuleUserDTO;

import java.util.List;

public interface ModulesService {

    List<ModuleUserDTO> getModuleUsers(Long userId, String rol);
}
