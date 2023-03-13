package br.dev.bs.shortenerapi.models.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDTO(
        @NotNull @NotBlank String name,
        @NotNull @NotBlank @Email String email,
        String password,
        @NotNull String role
) {
}
