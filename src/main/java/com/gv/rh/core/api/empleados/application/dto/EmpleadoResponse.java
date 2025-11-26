package com.gv.rh.core.api.empleados.application.dto;

import java.time.LocalDate;

public record EmpleadoResponse(
        Long id,
        String numEmpleado,
        String nombres,
        String apellidoPaterno,
        String apellidoMaterno,
        String telefono,
        String email,
        LocalDate fechaIngreso,
        Boolean activo
) {}
