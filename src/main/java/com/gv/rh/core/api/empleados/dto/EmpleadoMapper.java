package com.gv.rh.core.api.empleados.dto;

import com.gv.rh.core.api.empleados.domain.Empleado;

public class EmpleadoMapper {

    // ... otros métodos que ya tengas (toDto, toEntity, etc.)

    public static EmpleadoFichaDto toFichaDto(Empleado empleado) {
        EmpleadoFichaDto dto = new EmpleadoFichaDto();

        dto.setId(empleado.getId());
        dto.setNumEmpleado(empleado.getNumEmpleado());

        // Antes: setNombresCompletos(...)
        // Ahora: setNombreCompleto(...)
        String nombreCompleto = (empleado.getNombres() != null ? empleado.getNombres() : "") +
                (empleado.getApellidoPaterno() != null ? " " + empleado.getApellidoPaterno() : "") +
                (empleado.getApellidoMaterno() != null ? " " + empleado.getApellidoMaterno() : "");
        dto.setNombreCompleto(nombreCompleto.trim());

        // Antes: dto.setFechaIngreso(empleado.getFechaIngreso()); // LocalDate
        // Ahora lo convertimos a String
        dto.setFechaIngreso(
                empleado.getFechaIngreso() != null
                        ? empleado.getFechaIngreso().toString()   // yyyy-MM-dd
                        : null
        );

        dto.setTelefono(empleado.getTelefono());
        dto.setEmail(empleado.getEmail());

        // Estos los puedes ir llenando cuando tengas bien las relaciones
        dto.setPuesto(null);             // empleado.getPuesto().getNombre() si ya existe
        dto.setDepartamento(null);       // empleado.getDepartamento().getNombre()
        dto.setSupervisorNombre(null);   // supervisor si aplica

        dto.setCurp(empleado.getCurp());
        dto.setRfc(empleado.getRfc());
        dto.setNss(empleado.getNss());

        dto.setDireccionCompleta(null);  // aquí luego armamos la dirección formateada

        return dto;
    }
}
