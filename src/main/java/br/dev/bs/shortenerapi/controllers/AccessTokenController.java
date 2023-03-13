package br.dev.bs.shortenerapi.controllers;

import br.dev.bs.shortenerapi.models.dtos.AccessTokenDTO;
import br.dev.bs.shortenerapi.services.AccessTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/access-token")
public class AccessTokenController {

    private final AccessTokenService accessTokenService;


    @GetMapping
    public ResponseEntity<List<AccessTokenDTO>> getAllAccessToken() {
        return ResponseEntity.ok(accessTokenService.getAllAccessToken());
    }

    @GetMapping("/is-valid/{token}")
    public ResponseEntity isValid(@PathVariable String token) {
        try {
            if (accessTokenService.isValid(token))
                return ResponseEntity.ok().build();

            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<AccessTokenDTO> createAccessToken(@RequestBody AccessTokenDTO accessToken) {
        return ResponseEntity.ok(accessTokenService.createAccessToken(accessToken.expires_at()));
    }
}
