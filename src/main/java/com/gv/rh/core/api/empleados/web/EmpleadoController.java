package com.gv.rh.core.api.empleados.web;

import com.gv.rh.core.api.empleados.application.EmpleadoService;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoCreateRequest;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoResponse;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoUpdateRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Empleados", description = "Gestión de empleados")
@RestController
@RequestMapping("/api/empleados")
@CrossOrigin(origins = "http://localhost:5173") // Front React (Vite)
@RequiredArgsConstructor
public class EmpleadoController {

    private final EmpleadoService service;

    @Operation(summary = "Listar empleados paginados")
    @GetMapping
    public Page<EmpleadoResponse> listar(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return service.listar(pageable);
    }

    @Operation(summary = "Obtener un empleado por id")
    @GetMapping("/{id}")
    public EmpleadoResponse obtener(@PathVariable Long id) {
        return service.obtener(id);
    }

    @Operation(summary = "Crear un nuevo empleado")
    @PostMapping
    public ResponseEntity<EmpleadoResponse> crear(@Valid @RequestBody EmpleadoCreateRequest request) {
        EmpleadoResponse creado = service.crear(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(creado);
    }

    @Operation(summary = "Actualizar un empleado existente")
    @PutMapping("/{id}")
    public EmpleadoResponse actualizar(
            @PathVariable Long id,
            @Valid @RequestBody EmpleadoUpdateRequest request
    ) {
        return service.actualizar(id, request);
    }

    @Operation(summary = "Eliminar un empleado")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminar(@PathVariable Long id) {
        service.eliminar(id);
    }
}
