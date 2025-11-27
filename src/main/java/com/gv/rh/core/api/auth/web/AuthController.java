package com.gv.rh.core.api.auth.web;

import com.gv.rh.core.api.auth.dto.LoginRequest;
import com.gv.rh.core.api.auth.dto.LoginResponse;
import com.gv.rh.core.api.auth.jwt.JwtService;
import com.gv.rh.core.api.auth.service.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Endpoints de autenticación")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Value("${security.jwt.expiration-seconds:3600}")
    private long expirationSeconds;

    @PostMapping("/login")
    @Operation(summary = "Login con usuario y contraseña, devuelve JWT")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.username(),
                            request.password()
                    )
            );

            // Guardamos la autenticación en el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            var userDetails = userDetailsService.loadUserByUsername(request.username());
            String token = jwtService.generateToken(userDetails);

            List<String> roles = userDetails.getAuthorities().stream()
                    .map(a -> a.getAuthority())
                    .collect(Collectors.toList());

            // Usamos el helper estático del DTO
            LoginResponse response = LoginResponse.bearer(
                    token,
                    expirationSeconds,
                    userDetails.getUsername(),
                    roles
            );

            return ResponseEntity.ok(response);
        } catch (BadCredentialsException ex) {
            // Credenciales inválidas
            return ResponseEntity.status(401).build();
        }
    }
}
