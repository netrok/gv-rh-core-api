package com.gv.rh.core.api.empleados;

import com.gv.rh.core.api.empleados.report.EmpleadoReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoReportController {

    private final EmpleadoReportService empleadoReportService;

    @PreAuthorize("hasAnyAuthority('SUPERADMIN','ADMIN','RRHH')")
    @GetMapping("/{id}/ficha.pdf")
    public ResponseEntity<byte[]> fichaPdf(@PathVariable Long id) {
        byte[] pdf = empleadoReportService.generarFichaEmpleado(id);

        String filename = "ficha_empleado_" + id + ".pdf";

        return ResponseEntity.ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "inline; filename=\"" + filename + "\""
                )
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
