package com.gv.rh.core.api.empleados.application.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public record EmpleadoUpdateRequest(

        @NotBlank
        @Size(max = 100)
        String nombres,

        @NotBlank
        @Size(max = 100)
        String apellidoPaterno,

        @Size(max = 100)
        String apellidoMaterno,

        @Size(max = 20)
        String telefono,

        @Email
        @Size(max = 150)
        String email,

        @NotNull
        LocalDate fechaIngreso,

        @NotNull
        Boolean activo
) {}
