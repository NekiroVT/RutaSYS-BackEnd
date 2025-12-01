package pe.edu.upeu.g35.rutasys.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upeu.g35.rutasys.dto.ModuleUserDTO;
import pe.edu.upeu.g35.rutasys.entity.Administrador;
import pe.edu.upeu.g35.rutasys.entity.Chofer;
import pe.edu.upeu.g35.rutasys.entity.RolUsuario;
import pe.edu.upeu.g35.rutasys.entity.Usuario;
import pe.edu.upeu.g35.rutasys.repository.AdministradorRepository;
import pe.edu.upeu.g35.rutasys.repository.ChoferRepository;
import pe.edu.upeu.g35.rutasys.repository.UsuarioRepository;
import pe.edu.upeu.g35.rutasys.repository.RolUsuarioRepository;
import pe.edu.upeu.g35.rutasys.service.ModulesService;

import java.util.List;
import java.util.Optional;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class ModulesServiceImpl implements ModulesService {

    private final ChoferRepository choferRepository;
    private final AdministradorRepository administradorRepository;
    private final UsuarioRepository usuarioRepository;
    private final RolUsuarioRepository rolUsuarioRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ModuleUserDTO> getModuleUsers(Long userId, String rolFiltro) {

        String rolFiltroNorm = rolFiltro.toUpperCase();

        // 🟢 ADMINISTRADOR → listar todos los usuarios (incluyendo su propia imagen)
        if (rolFiltroNorm.equals("ADMINISTRADOR")) {

            List<Usuario> usuarios = usuarioRepository.findAll();

            return usuarios.stream().map(u -> {

                // ✅ Obtener rol desde la tabla puente rol_usuario
                List<RolUsuario> roles = rolUsuarioRepository.findByUsuarioId(u.getId());
                String rol = roles.isEmpty() ? "SIN_ROL" : roles.get(0).getRol().getNombre().toUpperCase();

                // 📌 BUSCAMOS DATOS DEL PERFIL (Nombre Completo e Imagen)
                Long choferId = null;
                Long administradorId = null;
                String profileName = u.getUsername(); // Valor por defecto
                String imageUrl = null; // Valor por defecto

                if (rol.equals("CHOFER")) {
                    Optional<Chofer> optChofer = choferRepository.findByUsuario_Id(u.getId());
                    if (optChofer.isPresent()) {
                        Chofer c = optChofer.get();
                        choferId = c.getId();
                        profileName = c.getNombreCompleto();
                        imageUrl = c.getImagenUrl();
                    }
                } else if (rol.equals("ADMINISTRADOR")) {
                    Optional<Administrador> optAdmin = administradorRepository.findByUsuarioId(u.getId());
                    if (optAdmin.isPresent()) {
                        Administrador a = optAdmin.get();
                        administradorId = a.getId();
                        profileName = a.getNombreCompleto();
                        imageUrl = a.getImagenUrl();
                    }
                }

                return ModuleUserDTO.builder()
                        .usuarioId(u.getId())
                        .choferId(choferId)
                        .administradorId(administradorId)
                        .nombre(profileName) // Usar el nombre del perfil encontrado
                        .rol(rol)
                        // ⬅️ CORREGIDO: Ruta fija al dashboard principal
                        .route("/viewuser")
                        .imagenUrl(imageUrl) // Usar la URL de imagen extraída
                        .build();

            }).toList();
        }

        // 🟡 CHOFER → solo 1
        if (rolFiltroNorm.equals("CHOFER")) {

            Optional<Chofer> opt = choferRepository.findByUsuario_Id(userId);
            if (opt.isEmpty()) return Collections.emptyList();

            Chofer c = opt.get();

            return List.of(
                    ModuleUserDTO.builder()
                            .usuarioId(c.getUsuario().getId())
                            .choferId(c.getId())
                            .administradorId(null)
                            .nombre(c.getNombreCompleto())
                            .rol("CHOFER")
                            // ⬅️ CORREGIDO: Ruta fija al dashboard principal
                            .route("/viewuser")
                            .imagenUrl(c.getImagenUrl())
                            .build()
            );
        }

        return Collections.emptyList();
    }
}