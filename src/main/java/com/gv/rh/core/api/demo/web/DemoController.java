package com.gv.rh.core.api.demo.web;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    @GetMapping("/public")
    public String publico() {
        return "Endpoint público (si lo marcamos como permitAll en SecurityConfig)";
    }

    @GetMapping("/solo-admin")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String soloAdmin() {
        return "Solo ADMIN puede ver esto";
    }

    @GetMapping("/solo-rrhh")
    @PreAuthorize("hasAuthority('RRHH')")
    public String soloRrhh() {
        return "Solo RRHH puede ver esto";
    }

    @GetMapping("/solo-superadmin")
    @PreAuthorize("hasAuthority('SUPERADMIN')")
    public String soloSuperadmin() {
        return "Solo SUPERADMIN puede ver esto";
    }

    @GetMapping("/admin-o-rrhh")
    @PreAuthorize("hasAnyAuthority('ADMIN','RRHH')")
    public String adminORrhh() {
        return "ADMIN o RRHH pueden ver esto";
    }
}
