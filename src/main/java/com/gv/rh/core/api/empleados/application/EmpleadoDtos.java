package com.gv.rh.core.api.empleados.application;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public class EmpleadoDtos {

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
    ) { }

    public record EmpleadoCreateRequest(
            @NotBlank String numEmpleado,
            @NotBlank String nombres,
            @NotBlank String apellidoPaterno,
            String apellidoMaterno,
            String telefono,
            @Email String email,
            @NotNull LocalDate fechaIngreso
    ) { }

    public record EmpleadoUpdateRequest(
            @NotBlank String nombres,
            @NotBlank String apellidoPaterno,
            String apellidoMaterno,
            String telefono,
            @Email String email,
            @NotNull LocalDate fechaIngreso,
            @NotNull Boolean activo
    ) { }
}
