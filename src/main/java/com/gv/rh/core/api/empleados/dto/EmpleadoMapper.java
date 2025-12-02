package com.gv.rh.core.api.empleados.dto;  // 👈 MISMO package que Empleado

import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.dto.EmpleadoFichaDto;

public final class EmpleadoMapper {

    private EmpleadoMapper() {
        // util class
    }

    public static EmpleadoFichaDto toFichaDto(Empleado e) {
        EmpleadoFichaDto dto = new EmpleadoFichaDto();

        dto.setId(e.getId());
        dto.setNumEmpleado(e.getNumEmpleado());

        // Nombre completo
        StringBuilder nombreCompleto = new StringBuilder();
        if (e.getNombres() != null) {
            nombreCompleto.append(e.getNombres()).append(" ");
        }
        if (e.getApellidoPaterno() != null) {
            nombreCompleto.append(e.getApellidoPaterno()).append(" ");
        }
        if (e.getApellidoMaterno() != null) {
            nombreCompleto.append(e.getApellidoMaterno());
        }
        dto.setNombresCompletos(nombreCompleto.toString().trim());

        // Campos seguros que ya tienes en la entidad:
        dto.setFechaIngreso(e.getFechaIngreso());
        dto.setTelefono(e.getTelefono());
        dto.setEmail(e.getEmail());

        // El resto los dejamos listos para rellenar cuando tu entidad los tenga:
        dto.setDireccionCompleta(null);
        dto.setPuesto(null);
        dto.setDepartamento(null);
        dto.setCurp(null);
        dto.setRfc(null);
        dto.setNss(null);
        dto.setSupervisorNombre(null);

        return dto;
    }

    /*
    private static String construirDireccion(Empleado e) {
        StringBuilder sb = new StringBuilder();
        if (e.getCalle() != null) sb.append(e.getCalle()).append(" ");
        if (e.getNumExt() != null) sb.append("#").append(e.getNumExt()).append(" ");
        if (e.getColonia() != null) sb.append(e.getColonia()).append(", ");
        if (e.getMunicipio() != null) sb.append(e.getMunicipio()).append(", ");
        if (e.getEstado() != null) sb.append(e.getEstado()).append(" ");
        if (e.getCp() != null) sb.append("CP ").append(e.getCp());
        return sb.toString().trim();
    }
    */
}
