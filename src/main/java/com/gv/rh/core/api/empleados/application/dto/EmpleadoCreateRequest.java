package com.gv.rh.core.api.empleados.application.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

public record EmpleadoCreateRequest(

        @NotBlank
        @Size(max = 20)
        String numEmpleado,

        @NotBlank
        @Size(max = 150)
        String nombres,

        @NotBlank
        @Size(max = 150)
        String apellidoPaterno,

        @Size(max = 150)
        String apellidoMaterno,

        @Size(max = 30)
        String telefono,

        @Email
        @Size(max = 150)
        String email,

        @NotNull
        LocalDate fechaIngreso,

        @NotNull
        Boolean activo,

        // ==== NUEVOS CAMPOS BÁSICOS ====
        LocalDate fechaNacimiento,
        @Size(max = 20)
        String genero,
        @Size(max = 30)
        String estadoCivil,
        @Size(max = 18)
        String curp,
        @Size(max = 13)
        String rfc,
        @Size(max = 15)
        String nss,
        @Size(max = 255)
        String foto,

        // ==== RELACIONES (IDs simples) ====
        Long departamentoId,
        Long puestoId,
        Long turnoId,
        Long horarioId,
        Long supervisorId,

        // ==== DOMICILIO ====
        @Size(max = 150)
        String calle,
        @Size(max = 20)
        String numExt,
        @Size(max = 20)
        String numInt,
        @Size(max = 150)
        String colonia,
        @Size(max = 150)
        String municipio,
        @Size(max = 150)
        String estado,
        @Size(max = 10)
        String cp,
        @Size(max = 80)
        String nacionalidad,
        @Size(max = 150)
        String lugarNacimiento,

        // ==== ESCOLARIDAD / MÉDICO ====
        @Size(max = 80)
        String escolaridad,
        @Size(max = 10)
        String tipoSangre,

        // ==== CONTACTO DE EMERGENCIA ====
        @Size(max = 150)
        String contactoNombre,
        @Size(max = 30)
        String contactoTelefono,
        @Size(max = 80)
        String contactoParentesco,

        // ==== BANCARIO / NÓMINA ====
        @Size(max = 80)
        String banco,
        @Size(max = 30)
        String cuentaBancaria,
        @Size(max = 30)
        String clabe,
        BigDecimal salarioBase,
        @Size(max = 50)
        String tipoContrato,
        @Size(max = 50)
        String tipoJornada,
        LocalDate fechaBaja,
        @Size(max = 150)
        String motivoBaja,

        // ==== IMSS / INFONAVIT / FONACOT ====
        @Size(max = 50)
        String imssRegPatronal,
        @Size(max = 30)
        String infonavitNumero,
        @Size(max = 20)
        String infonavitDescuentoTipo,
        BigDecimal infonavitDescuentoValor,
        @Size(max = 30)
        String fonacotNumero,

        // ==== LICENCIA ====
        @Size(max = 30)
        String licenciaNumero,
        @Size(max = 30)
        String licenciaTipo,
        LocalDate licenciaVigencia
) {}
