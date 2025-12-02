package com.gv.rh.core.api.empleados;

import com.gv.rh.core.api.empleados.report.EmpleadoReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
public class EmpleadoReportController {

    private final EmpleadoReportService reportService;

    public EmpleadoReportController(EmpleadoReportService reportService) {
        this.reportService = reportService;
    }

    @PreAuthorize("hasAnyAuthority('SUPERADMIN','ADMIN','RRHH')")
    @GetMapping("/{id}/ficha.pdf")
    public ResponseEntity<byte[]> descargarFicha(@PathVariable Long id) {
        byte[] pdf = reportService.generarFichaEmpleado(id);

        String filename = "ficha_empleado_" + id + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
