package com.gv.rh.core.api.empleados.report;

import com.gv.rh.core.api.core.error.NotFoundException;
import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import com.gv.rh.core.api.empleados.dto.EmpleadoFichaDto;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmpleadoReportServiceImpl implements EmpleadoReportService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoReportServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public byte[] generarFichaEmpleado(Long empleadoId) {
        Empleado empleado = empleadoRepository.findById(empleadoId)
                .orElseThrow(() -> new NotFoundException("Empleado no encontrado con id " + empleadoId));

        EmpleadoFichaDto dto = toFichaDto(empleado);

        try {
            InputStream jrxmlStream = getClass().getResourceAsStream("/reports/empleado_ficha.jrxml");
            if (jrxmlStream == null) {
                throw new IllegalStateException("No se encontró la plantilla /reports/empleado_ficha.jrxml");
            }

            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            JRBeanCollectionDataSource dataSource =
                    new JRBeanCollectionDataSource(List.of(dto));

            Map<String, Object> params = new HashMap<>();

            // Logo corporativo
            String logoPath = "uploads/logo/logo_gv.png";
            if (Files.exists(Paths.get(logoPath))) {
                params.put("LOGO_PATH", logoPath);
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            throw new RuntimeException("Error al generar ficha PDF del empleado", e);
        }
    }

    // ----------------- Mapeo al DTO de la ficha -----------------

    private EmpleadoFichaDto toFichaDto(Empleado e) {
        EmpleadoFichaDto dto = new EmpleadoFichaDto();

        dto.setId(e.getId());
        dto.setNumEmpleado(safe(e.getNumEmpleado()));

        // Nombre completo
        StringBuilder nombreCompleto = new StringBuilder();
        if (e.getNombres() != null && !e.getNombres().isBlank()) {
            nombreCompleto.append(e.getNombres().trim()).append(" ");
        }
        if (e.getApellidoPaterno() != null && !e.getApellidoPaterno().isBlank()) {
            nombreCompleto.append(e.getApellidoPaterno().trim()).append(" ");
        }
        if (e.getApellidoMaterno() != null && !e.getApellidoMaterno().isBlank()) {
            nombreCompleto.append(e.getApellidoMaterno().trim());
        }
        dto.setNombreCompleto(nombreCompleto.toString().trim());

        // Iniciales para el círculo
        dto.setIniciales(calcularIniciales(e));

        // Activo como boolean
        dto.setActivo(Boolean.TRUE.equals(e.getActivo()));

        // Como aún no tenemos relaciones, mostramos guion
        dto.setPuesto("—");
        dto.setDepartamento("—");
        dto.setSupervisorNombre("—");

        // Fecha ingreso como String
        dto.setFechaIngreso(
                e.getFechaIngreso() != null
                        ? e.getFechaIngreso().toString()  // yyyy-MM-dd
                        : ""
        );

        // Datos de contacto
        dto.setTelefono(safe(e.getTelefono()));
        dto.setEmail(safeUpper(e.getEmail()));

        // Dirección completa (por ahora vacía)
        dto.setDireccionCompleta(construirDireccion(e));

        // Datos fiscales / seguridad social
        dto.setCurp(safeUpper(e.getCurp()));
        dto.setRfc(safeUpper(e.getRfc()));
        dto.setNss(safe(e.getNss()));

        return dto;
    }

    // ----------------- Helpers internos -----------------

    private String safe(String value) {
        return value != null ? value : "";
    }

    private String safeUpper(String value) {
        return value != null ? value.toUpperCase() : "";
    }

    private String calcularIniciales(Empleado e) {
        StringBuilder sb = new StringBuilder();
        if (e.getNombres() != null && !e.getNombres().isBlank()) {
            sb.append(e.getNombres().trim().charAt(0));
        }
        if (e.getApellidoPaterno() != null && !e.getApellidoPaterno().isBlank()) {
            sb.append(e.getApellidoPaterno().trim().charAt(0));
        }
        return sb.toString().toUpperCase();
    }

    private String construirDireccion(Empleado e) {
        // Cuando tengas calle, numExt, colonia, etc., lo armas aquí.
        // Por ahora devolvemos vacío para que Jasper no muestre "null".
        return "";
    }
}
