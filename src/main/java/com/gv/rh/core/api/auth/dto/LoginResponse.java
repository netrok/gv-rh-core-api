package com.gv.rh.core.api.auth.dto;

import java.util.List;

public record LoginResponse(
        String accessToken,
        String tokenType,
        long expiresIn,
        String username,
        List<String> roles
) {
    public static LoginResponse bearer(String token, long expiresIn, String username, List<String> roles) {
        return new LoginResponse(token, "Bearer", expiresIn, username, roles);
    }
}
