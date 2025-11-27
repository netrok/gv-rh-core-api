package com.gv.rh.core.api.auth.dto;

public record LoginRequest(
        String username,
        String password
) {}
