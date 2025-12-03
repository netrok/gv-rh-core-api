package com.gv.rh.core.api.empleados.dto;

import com.gv.rh.core.api.empleados.domain.Empleado;

import java.time.format.DateTimeFormatter;

// =====================
// Mapper para DTO
// =====================
public class EmpleadoMapper {

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    // ... otros métodos que ya tengas (toDto, toEntity, etc.)

    public static EmpleadoFichaDto toFichaDto(Empleado empleado) {
        EmpleadoFichaDto dto = new EmpleadoFichaDto();

        dto.setId(empleado.getId());
        dto.setNumEmpleado(empleado.getNumEmpleado());

        // Nombre completo ya bien armado
        dto.setNombreCompleto(buildNombreCompleto(empleado));

        // Fecha de ingreso formateada dd/MM/yyyy
        dto.setFechaIngreso(formatDate(empleado.getFechaIngreso()));

        dto.setTelefono(empleado.getTelefono());
        dto.setEmail(empleado.getEmail());

        // Relaciones básicas
        dto.setPuesto(
                empleado.getPuesto() != null
                        ? nullSafe(empleado.getPuesto().getNombre())
                        : null
        );

        dto.setDepartamento(
                empleado.getDepartamento() != null
                        ? nullSafe(empleado.getDepartamento().getNombre())
                        : null
        );

        dto.setSupervisorNombre(buildSupervisorNombre(empleado));

        // Identificadores
        dto.setCurp(empleado.getCurp());
        dto.setRfc(empleado.getRfc());
        dto.setNss(empleado.getNss());

        // Dirección formateada
        dto.setDireccionCompleta(buildDireccionCompleta(empleado));

        return dto;
    }

    // ===== Helpers reutilizables =====

    static String nullSafe(String value) {
        return value != null ? value : "";
    }

    static String formatDate(java.time.LocalDate date) {
        return date != null ? DATE_FORMAT.format(date) : null;
    }

    static String buildNombreCompleto(Empleado e) {
        StringBuilder sb = new StringBuilder();
        if (e.getNombres() != null) sb.append(e.getNombres());
        if (e.getApellidoPaterno() != null) sb.append(" ").append(e.getApellidoPaterno());
        if (e.getApellidoMaterno() != null) sb.append(" ").append(e.getApellidoMaterno());
        return sb.toString().trim();
    }

    static String buildDireccionCompleta(Empleado e) {
        String calle = nullSafe(e.getCalle());
        String numExt = nullSafe(e.getNumExt());
        String numInt = nullSafe(e.getNumInt());
        String numIntParte = !numInt.isBlank() ? " Int. " + numInt : "";

        String colonia = nullSafe(e.getColonia());
        String cp = nullSafe(e.getCp());
        String municipio = nullSafe(e.getMunicipio());
        String estado = nullSafe(e.getEstado());

        String dir = String.format(
                "%s %s%s, %s, C.P. %s, %s, %s",
                calle, numExt, numIntParte, colonia, cp, municipio, estado
        );

        return dir.replaceAll("\\s+", " ").trim();
    }

    static String buildSupervisorNombre(Empleado e) {
        if (e.getSupervisor() == null) {
            return null;
        }
        Empleado sup = e.getSupervisor();
        StringBuilder sb = new StringBuilder();
        if (sup.getNombres() != null) sb.append(sup.getNombres());
        if (sup.getApellidoPaterno() != null) sb.append(" ").append(sup.getApellidoPaterno());
        if (sup.getApellidoMaterno() != null) sb.append(" ").append(sup.getApellidoMaterno());
        String result = sb.toString().trim();
        return result.isEmpty() ? null : result;
    }
}
