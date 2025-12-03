package com.gv.rh.core.api.empleados;

import com.gv.rh.core.api.empleados.report.EmpleadoReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/empleados")
@Tag(name = "Empleados - Reportes")
@RequiredArgsConstructor
public class EmpleadoReportController {

    private final EmpleadoReportService empleadoReportService;

    @GetMapping("/{id}/ficha.pdf")
    @PreAuthorize("hasAnyAuthority('SUPERADMIN','ADMIN','RRHH')")
    @Operation(
            summary = "Ficha PDF del empleado",
            description = "Genera la ficha corporativa del empleado en PDF",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    public ResponseEntity<byte[]> fichaPdf(@PathVariable("id") Long id) {
        byte[] pdfBytes = empleadoReportService.generarFichaEmpleado(id);

        String filename = "empleado_" + id + "_ficha.pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
