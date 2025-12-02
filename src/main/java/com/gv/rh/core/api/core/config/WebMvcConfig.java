package com.gv.rh.core.api.core.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${app.uploads-dir}")
    private String uploadsDir;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // Sirve archivos estáticos desde la carpeta física "uploads"
        // http://localhost:8080/uploads/** -> <proyecto>/uploads/**
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:" + uploadsDir + "/");
    }
}
