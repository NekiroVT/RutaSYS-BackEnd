package pe.edu.upeu.g35.rutasys.controller.general;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.g35.rutasys.dto.ModuleUserDTO;
import pe.edu.upeu.g35.rutasys.service.ModulesService;
import pe.edu.upeu.g35.rutasys.service.security.JwtTokenProvider;

import java.util.List;
import java.util.Collections;

@RestController
@RequestMapping("/api/modules")
@RequiredArgsConstructor
public class ModuleController {

    private final ModulesService modulesService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/users")
    public ResponseEntity<List<ModuleUserDTO>> getModuleUsers(
            @RequestHeader("Authorization") String authorizationHeader) {

        // 🛡️ 1. Verificar formato Bearer
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.emptyList());
        }

        // ✂️ 2. Extraer token (sin "Bearer ")
        String token = authorizationHeader.substring(7).trim();

        // 🔐 3. Validar token
        if (!jwtTokenProvider.validateToken(token)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.emptyList());
        }

        // 🎯 4. Leer datos desde el JWT
        Long userId = Long.valueOf(jwtTokenProvider.getUserIdFromToken(token));
        List<String> roles = jwtTokenProvider.getRolesFromToken(token);

        // ✅ 5. Log para debug
        System.out.println("Roles en JWT = " + roles);

        // 🔎 6. Determinar solo 1 rol para filtrado lógico
        String rolFiltro;
        if (roles.contains("ADMIN") || roles.contains("ADMINISTRADOR")) {
            rolFiltro = "ADMINISTRADOR"; // ⬅️ CORRECCIÓN: Usar el nombre completo del rol
        } else if (roles.contains("CHOFER")) {
            rolFiltro = "CHOFER";
        } else {
            // ❌ Si no tiene rol válido → devolver vacío sin afectar token
            return ResponseEntity.ok(Collections.emptyList());
        }

        // 🔍 7. Llamar al servicio para listar modules por rol
        List<ModuleUserDTO> result = modulesService.getModuleUsers(userId, rolFiltro);

        // ✅ 8. Responder OK
        return ResponseEntity.ok(result);
    }
}