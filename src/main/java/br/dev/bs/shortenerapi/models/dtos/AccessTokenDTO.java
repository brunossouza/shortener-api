package br.dev.bs.shortenerapi.models.dtos;

import java.util.Date;

public record AccessTokenDTO(
        String token,
        String userId,
        Date expires_at
) {
}
