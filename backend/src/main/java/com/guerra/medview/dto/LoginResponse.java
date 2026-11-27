package com.guerra.medview.dto;

public record LoginResponse(String token, String tokenType, long expiresIn) {
}
