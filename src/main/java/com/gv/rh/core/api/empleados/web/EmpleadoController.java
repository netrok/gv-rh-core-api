// src/main/java/com/gv/rh/core/api/empleados/web/EmpleadoController.java
package com.gv.rh.core.api.empleados.web;

import com.gv.rh.core.api.empleados.application.EmpleadoService;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoCreateRequest;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoResponse;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Empleados", description = "Gestión de empleados")
@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth") // Todos los endpoints de este controller requieren JWT
public class EmpleadoController {

    private final EmpleadoService service;

    @Operation(summary = "Listar empleados paginados (búsqueda + filtro activo opcionales)")
    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','RRHH','SUPERADMIN')")
    public Page<EmpleadoResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) String q,
            @RequestParam(required = false) Boolean activo
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listar(pageable, q, activo);
    }

    @Operation(summary = "Obtener un empleado por id")
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RRHH','SUPERADMIN')")
    public EmpleadoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear un nuevo empleado")
    @PostMapping
    @PreAuthorize("hasAnyAuthority('ADMIN','RRHH','SUPERADMIN')")
    public ResponseEntity<EmpleadoResponse> crear(@Valid @RequestBody EmpleadoCreateRequest request) {
        EmpleadoResponse creado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un empleado existente")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN','RRHH','SUPERADMIN')")
    public EmpleadoResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoUpdateRequest request
    ) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar un empleado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
