package br.dev.bs.shortenerapi.controllers;

import br.dev.bs.shortenerapi.services.ShortURLService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/r")
@RequiredArgsConstructor
public class RedirectController {

    private final ShortURLService service;

    @GetMapping("/{hashCode}")
    public ResponseEntity redirect(@PathVariable String hashCode) {
        var shortURL = service.getURLByHashCode(hashCode);
        if (shortURL == null)
            return ResponseEntity.notFound().build();

        // check if the URL is expired
        if (service.isExpired(shortURL))
            return ResponseEntity.status(HttpStatus.GONE).build();

        service.incrementHits(shortURL);

        return ResponseEntity
                .status(HttpStatus.FOUND)
                .header("Location", shortURL.getUrl())
                .build();
    }

}
