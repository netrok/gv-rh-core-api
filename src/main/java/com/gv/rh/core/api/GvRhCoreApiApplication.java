package com.gv.rh.core.api;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "GV RH Core API",
                version = "v1",
                description = "Backend principal de Recursos Humanos (empleados, auth, y más)."
        )
)
public class GvRhCoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GvRhCoreApiApplication.class, args);
    }
}
