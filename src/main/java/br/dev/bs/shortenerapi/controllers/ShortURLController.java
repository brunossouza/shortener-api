package br.dev.bs.shortenerapi.controllers;

import br.dev.bs.shortenerapi.models.dtos.ShortURLDTO;
import br.dev.bs.shortenerapi.services.AccessTokenService;
import br.dev.bs.shortenerapi.services.ShortURLService;
import br.dev.bs.shortenerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shortener")
public class ShortURLController {

    private final ShortURLService service;
    private final AccessTokenService accessTokenService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<ShortURLDTO>> getAllURLs() {
        return ResponseEntity.ok(service.getAllURLs());
    }

    @GetMapping("/{id}")
    public ResponseEntity getURLById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(service.getById(id));
        } catch (Exception e) {
            return new ResponseEntity(e.getMessage(), null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<ShortURLDTO> createURL(@RequestBody ShortURLDTO shortURL) {
        if (shortURL.url() == null || shortURL.url().isEmpty())
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(service.createURL(shortURL));
    }

    @PostMapping("/application/{accessToken}/create")
    public ResponseEntity<ShortURLDTO> createURLWithAccessToken(@PathVariable String accessToken, @RequestBody ShortURLDTO shortURL) {
        if (shortURL.url() == null || shortURL.url().isEmpty())
            return ResponseEntity.badRequest().build();

        if (!accessTokenService.isValid(accessToken))
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(service.createURLApplication(accessToken, shortURL));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShortURLDTO> updateURL(@PathVariable String id, @RequestBody ShortURLDTO shortURL) {
        if (shortURL.url() == null || shortURL.url().isEmpty())
            return ResponseEntity.badRequest().build();

        if (!id.equals(shortURL.id()))
            return ResponseEntity.badRequest().build();

        return ResponseEntity.ok(service.updateURL(id, shortURL));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteURL(@PathVariable String id) {
        try {
            service.deleteURL(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
        }
    }
}
