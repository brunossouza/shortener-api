package br.dev.bs.shortenerapi.models.dtos;

import java.util.Calendar;

public record UserDTO(
    String id,
    String name,
    String email,
    String role
) {
}
