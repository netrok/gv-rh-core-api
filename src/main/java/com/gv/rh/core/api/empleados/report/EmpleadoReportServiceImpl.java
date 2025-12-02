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

            // Logo corporativo (ajusta la ruta si es otra)
            String logoPath = "uploads/logo/logo_gv.png";
            if (Files.exists(Paths.get(logoPath))) {
                params.put("LOGO_PATH", logoPath);
            }

            // Por ahora SIN foto, luego la activamos cuando sepamos el campo real en la entidad

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

            return JasperExportManager.exportReportToPdf(jasperPrint);

        } catch (JRException e) {
            throw new RuntimeException("Error al generar ficha PDF del empleado", e);
        }
    }

    private EmpleadoFichaDto toFichaDto(Empleado e) {
        EmpleadoFichaDto dto = new EmpleadoFichaDto();

        dto.setId(e.getId());
        dto.setNumEmpleado(e.getNumEmpleado());

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

        dto.setFechaIngreso(e.getFechaIngreso());
        dto.setTelefono(e.getTelefono());
        dto.setEmail(e.getEmail());

        // Campos que luego llenamos cuando estén amarrados en la entidad
        dto.setPuesto(null);
        dto.setDepartamento(null);
        dto.setCurp(null);
        dto.setRfc(null);
        dto.setNss(null);
        dto.setDireccionCompleta(null);
        dto.setSupervisorNombre(null);

        return dto;
    }
}
