package br.dev.bs.shortenerapi.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public record ShortURLDTO(
        String id,
        @NotNull @NotBlank String url, // url_original

        String url_shortened,

        String code,
        Long hits,
        Date created,
        Date updated,
        Date expires_in,
        String user_id
) {
    public ShortURLDTO withUserId(String userId) {
        return new ShortURLDTO(id, url, url_shortened, code, hits, created, updated, expires_in, userId);
    }
}
