package com.gv.rh.core.api.empleados.application.dto;

import java.math.BigDecimal;
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
        Boolean activo,

        // NUEVOS CAMPOS
        LocalDate fechaNacimiento,
        String genero,
        String estadoCivil,
        String curp,
        String rfc,
        String nss,
        String foto,

        Long departamentoId,
        Long puestoId,
        Long turnoId,
        Long horarioId,
        Long supervisorId,

        String calle,
        String numExt,
        String numInt,
        String colonia,
        String municipio,
        String estado,
        String cp,
        String nacionalidad,
        String lugarNacimiento,

        String escolaridad,
        String tipoSangre,

        String contactoNombre,
        String contactoTelefono,
        String contactoParentesco,

        String banco,
        String cuentaBancaria,
        String clabe,
        String tipoContrato,
        String tipoJornada,
        LocalDate fechaBaja,
        String motivoBaja,

        String imssRegPatronal,
        String infonavitNumero,
        String fonacotNumero
) {}
