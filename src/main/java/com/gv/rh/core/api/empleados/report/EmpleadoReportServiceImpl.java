package com.gv.rh.core.api.empleados.report;

import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class EmpleadoReportServiceImpl implements EmpleadoReportService {

    private final EmpleadoRepository empleadoRepository;

    public EmpleadoReportServiceImpl(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    @Override
    public byte[] generarFichaEmpleado(Long empleadoId) {
        try {
            // 1) Obtener empleado de BD
            Empleado emp = empleadoRepository.findById(empleadoId)
                    .orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado: " + empleadoId));

            // 2) Mapear a DTO de reporte
            EmpleadoFichaReportRow row = mapToReportRow(emp);

            // 3) Cargar JRXML desde classpath
            ClassPathResource jrxml = new ClassPathResource("reports/empleado_ficha.jrxml");
            try (InputStream is = jrxml.getInputStream()) {
                JasperReport jasperReport = JasperCompileManager.compileReport(is);

                // 4) Parámetros
                Map<String, Object> params = new HashMap<>();
                // TODO: cuando tengas el logo físico, ajusta esto:
                // ClassPathResource logo = new ClassPathResource("static/images/logo_gv.png");
                // params.put("LOGO_PATH", logo.getFile().getAbsolutePath());
                params.put("LOGO_PATH", "");

                // 5) DataSource (una sola fila)
                JRBeanCollectionDataSource ds =
                        new JRBeanCollectionDataSource(Collections.singletonList(row));

                JasperPrint print = JasperFillManager.fillReport(jasperReport, params, ds);

                // 6) Exportar a PDF
                return JasperExportManager.exportReportToPdf(print);
            }

        } catch (Exception e) {
            throw new RuntimeException("Error al generar ficha PDF del empleado", e);
        }
    }

    private EmpleadoFichaReportRow mapToReportRow(Empleado emp) {
        EmpleadoFichaReportRow r = new EmpleadoFichaReportRow();

        // === Identidad
        r.setNombreCompleto(buildNombreCompleto(emp));
        r.setNumEmpleado(emp.getNumEmpleado());
        r.setIniciales(buildIniciales(emp));
        r.setActivo(emp.getActivo());

        // === Puesto / organización
        // De momento solo tenemos IDs. Los mostramos como texto simple o los dejamos vacíos.
        r.setPuesto(emp.getPuestoId() != null ? "ID " + emp.getPuestoId() : "");
        r.setDepartamento(emp.getDepartamentoId() != null ? "ID " + emp.getDepartamentoId() : "");
        r.setFechaIngreso(formatDate(emp.getFechaIngreso()));
        r.setSupervisorNombre(emp.getSupervisorId() != null ? "ID " + emp.getSupervisorId() : "");

        // === Contacto / domicilio
        r.setTelefono(emp.getTelefono());
        r.setEmail(emp.getEmail());
        r.setDireccionCompleta(buildDireccion(emp));

        // === Identificadores personales
        r.setCurp(emp.getCurp());
        r.setRfc(emp.getRfc());
        r.setNss(emp.getNss());

        // === Datos personales extra
        r.setFechaNacimiento(formatDate(emp.getFechaNacimiento()));
        r.setGenero(emp.getGenero());
        r.setEstadoCivil(emp.getEstadoCivil());
        r.setNacionalidad(emp.getNacionalidad());
        r.setLugarNacimiento(emp.getLugarNacimiento());
        r.setTipoSangre(emp.getTipoSangre());

        // === Datos laborales / bancarios
        r.setBanco(emp.getBanco());
        r.setCuentaBancaria(emp.getCuentaBancaria());
        r.setClabe(emp.getClabe());
        r.setTipoContrato(emp.getTipoContrato());
        r.setTipoJornada(emp.getTipoJornada());
        r.setFechaBaja(formatDate(emp.getFechaBaja()));
        r.setMotivoBaja(emp.getMotivoBaja());

        // === Seguridad social / créditos
        r.setImssRegPatronal(emp.getImssRegPatronal());
        r.setInfonavitNumero(emp.getInfonavitNumero());
        r.setFonacotNumero(emp.getFonacotNumero());

        // === Contacto emergencia
        r.setContactoNombre(emp.getContactoNombre());
        r.setContactoParentesco(emp.getContactoParentesco());
        r.setContactoTelefono(emp.getContactoTelefono());

        return r;
    }

    // ================= Helpers =================

    private String buildNombreCompleto(Empleado e) {
        StringBuilder sb = new StringBuilder();
        if (e.getNombres() != null && !e.getNombres().isBlank()) {
            sb.append(e.getNombres().trim());
        }
        if (e.getApellidoPaterno() != null && !e.getApellidoPaterno().isBlank()) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(e.getApellidoPaterno().trim());
        }
        if (e.getApellidoMaterno() != null && !e.getApellidoMaterno().isBlank()) {
            if (!sb.isEmpty()) sb.append(" ");
            sb.append(e.getApellidoMaterno().trim());
        }
        return sb.toString();
    }

    private String formatDate(Object d) {
        if (d == null) return "";
        try {
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            if (d instanceof LocalDate ld) {
                return ld.format(fmt);
            }
            if (d instanceof LocalDateTime ldt) {
                return ldt.toLocalDate().format(fmt);
            }
            if (d instanceof Date date) {
                return new SimpleDateFormat("dd/MM/yyyy").format(date);
            }
            return d.toString();
        } catch (Exception ex) {
            return "";
        }
    }

    private String buildIniciales(Empleado e) {
        if (e == null) return "";
        String nombres = e.getNombres();
        String apePat  = e.getApellidoPaterno();
        String apeMat  = e.getApellidoMaterno();

        StringBuilder sb = new StringBuilder();
        if (nombres != null && !nombres.isBlank()) {
            sb.append(Character.toUpperCase(nombres.trim().charAt(0)));
        }
        if (apePat != null && !apePat.isBlank()) {
            sb.append(Character.toUpperCase(apePat.trim().charAt(0)));
        }
        if (apeMat != null && !apeMat.isBlank()) {
            sb.append(Character.toUpperCase(apeMat.trim().charAt(0)));
        }
        return sb.toString();
    }

    private String buildDireccion(Empleado e) {
        List<String> partes = new ArrayList<>();
        if (e.getCalle() != null && !e.getCalle().isBlank()) partes.add(e.getCalle().trim());
        if (e.getNumExt() != null && !e.getNumExt().isBlank()) partes.add("No. " + e.getNumExt().trim());
        if (e.getColonia() != null && !e.getColonia().isBlank()) partes.add(e.getColonia().trim());
        if (e.getMunicipio() != null && !e.getMunicipio().isBlank()) partes.add(e.getMunicipio().trim());
        if (e.getEstado() != null && !e.getEstado().isBlank()) partes.add(e.getEstado().trim());
        if (e.getCp() != null && !e.getCp().isBlank()) partes.add("CP " + e.getCp().trim());
        return String.join(", ", partes);
    }
}
