package com.gv.rh.core.api.empleados.application;

import com.gv.rh.core.api.empleados.application.dto.EmpleadoCreateRequest;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoResponse;
import com.gv.rh.core.api.empleados.application.dto.EmpleadoUpdateRequest;
import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;

    @Transactional(readOnly = true)
    public Page<EmpleadoResponse> listar(Pageable pageable, String q, Boolean activo) {
        String term = (q == null || q.isBlank()) ? null : q.trim();
        return empleadoRepository
                .search(term, activo, pageable)
                .map(this::toResponse);
    }

    @Transactional(readOnly = true)
    public EmpleadoResponse obtener(Long id) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id " + id));
        return toResponse(empleado);
    }

    public EmpleadoResponse crear(EmpleadoCreateRequest request) {
        if (empleadoRepository.existsByNumEmpleado(request.numEmpleado())) {
            throw new IllegalArgumentException("Ya existe un empleado con número " + request.numEmpleado());
        }

        Empleado empleado = Empleado.builder()
                .numEmpleado(request.numEmpleado())
                .nombres(request.nombres())
                .apellidoPaterno(request.apellidoPaterno())
                .apellidoMaterno(request.apellidoMaterno())
                .telefono(request.telefono())
                .email(request.email())
                .fechaIngreso(request.fechaIngreso())
                .activo(true)
                .build();

        Empleado guardado = empleadoRepository.save(empleado);
        return toResponse(guardado);
    }

    public EmpleadoResponse actualizar(Long id, EmpleadoUpdateRequest request) {
        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Empleado no encontrado con id " + id));

        empleado.setNombres(request.nombres());
        empleado.setApellidoPaterno(request.apellidoPaterno());
        empleado.setApellidoMaterno(request.apellidoMaterno());
        empleado.setTelefono(request.telefono());
        empleado.setEmail(request.email());
        empleado.setFechaIngreso(request.fechaIngreso());
        empleado.setActivo(request.activo());

        Empleado actualizado = empleadoRepository.save(empleado);
        return toResponse(actualizado);
    }

    public void eliminar(Long id) {
        if (!empleadoRepository.existsById(id)) {
            throw new EntityNotFoundException("Empleado no encontrado con id " + id);
        }
        empleadoRepository.deleteById(id);
    }

    private EmpleadoResponse toResponse(Empleado e) {
        return new EmpleadoResponse(
                e.getId(),
                e.getNumEmpleado(),
                e.getNombres(),
                e.getApellidoPaterno(),
                e.getApellidoMaterno(),
                e.getTelefono(),
                e.getEmail(),
                e.getFechaIngreso(),
                e.getActivo()
        );
    }
}
