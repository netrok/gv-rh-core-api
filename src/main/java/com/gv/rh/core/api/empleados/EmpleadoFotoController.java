package com.gv.rh.core.api.empleados;

import com.gv.rh.core.api.empleados.domain.Empleado;
import com.gv.rh.core.api.empleados.domain.EmpleadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/empleados")
@RequiredArgsConstructor
public class EmpleadoFotoController {

    private final EmpleadoRepository empleadoRepository;

    // Si no está en application.yml, usa ./uploads por defecto
    @Value("${app.uploads-dir:./uploads}")
    private String uploadsDir;

    @PostMapping("/{id}/foto")
    public ResponseEntity<?> uploadFoto(
            @PathVariable Long id,
            @RequestParam("file") MultipartFile file
    ) throws IOException {

        Empleado empleado = empleadoRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Empleado no encontrado"
                ));

        if (file.isEmpty()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Archivo vacío"
            );
        }

        String originalName = file.getOriginalFilename();
        String fileName = (originalName != null && !originalName.isBlank())
                ? originalName
                : "foto-" + id + ".jpg";

        // Ruta relativa que guardamos en BD y que el front usará:
        // empleados/{id}/{archivo}
        String relativePath = "empleados/" + id + "/" + fileName;

        // Ruta física absoluta: {uploadsDir}/empleados/{id}/{archivo}
        Path targetPath = Paths.get(uploadsDir)
                .toAbsolutePath()
                .normalize()
                .resolve(relativePath)
                .normalize();

        // Crea carpetas si no existen (uploads, empleados, {id})
        Files.createDirectories(targetPath.getParent());

        // Guarda/reescribe el archivo
        Files.copy(file.getInputStream(), targetPath,
                java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Guarda la ruta relativa en la BD
        empleado.setFoto(relativePath);
        empleadoRepository.save(empleado);

        // 200 OK sin cuerpo; el front solo necesita saber que fue exitoso
        return ResponseEntity.ok().build();
    }
}
