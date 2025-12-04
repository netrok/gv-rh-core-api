package com.gv.rh.core.api.empleados.report;

import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
            InputStream jrxmlStream = getClass().getResourceAsStream("/reports/empleado_ficha.jrxml");
            if (jrxmlStream == null) {
                throw new IllegalStateException("No se encontró /reports/empleado_ficha.jrxml en el classpath");
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(jrxmlStream);

            // 4) Parámetros
            Map<String, Object> params = new HashMap<>();

            // LOGO CORPORATIVO (parámetro LOGO_GV tipo java.io.InputStream en el JRXML)
            InputStream logoStream = getClass().getResourceAsStream("/reports/img/logo-gv.png");
            if (logoStream != null) {
                params.put("LOGO_GV", logoStream);
            }

            // FOTO DEL EMPLEADO (parámetro FOTO_EMPLEADO tipo java.io.InputStream en el JRXML)
            InputStream fotoStream = null;

            // Suponemos que emp.getFoto() devuelve un String con el nombre/ruta del archivo
            String fotoPath = emp.getFoto(); // <-- AQUÍ estaba el problema antes

            if (fotoPath != null && !fotoPath.isBlank()) {
                try {
                    // Si en WebMvcConfig mapeaste "uploads" como raíz, usamos eso
                    Path uploadsRoot = Paths.get("uploads"); // ajusta si tu raíz es distinta
                    Path fullPath = uploadsRoot.resolve(fotoPath).normalize();

                    if (Files.exists(fullPath)) {
                        fotoStream = Files.newInputStream(fullPath);
                    }
                } catch (IOException e) {
                    // Aquí podrías loggear un warning si quieres
                    // log.warn("No se pudo leer la foto del empleado", e);
                }
            }

            // Si no hay foto, usamos placeholder
            if (fotoStream == null) {
                fotoStream = getClass().getResourceAsStream("/reports/img/foto-placeholder.png");
            }

            if (fotoStream != null) {
                params.put("FOTO_EMPLEADO", fotoStream);
            }

            // 5) DataSource (una sola fila)
            JRBeanCollectionDataSource ds =
                    new JRBeanCollectionDataSource(Collections.singletonList(row));

            // 6) Llenar y exportar a PDF
            JasperPrint print = JasperFillManager.fillReport(jasperReport, params, ds);
            return JasperExportManager.exportReportToPdf(print);

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
            if (sb.length() > 0) sb.append(" ");
            sb.append(e.getApellidoPaterno().trim());
        }
        if (e.getApellidoMaterno() != null && !e.getApellidoMaterno().isBlank()) {
            if (sb.length() > 0) sb.append(" ");
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
