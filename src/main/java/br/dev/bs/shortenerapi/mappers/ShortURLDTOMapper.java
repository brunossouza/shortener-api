package br.dev.bs.shortenerapi.mappers;

import br.dev.bs.shortenerapi.models.ShortURL;
import br.dev.bs.shortenerapi.models.dtos.ShortURLDTO;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class ShortURLDTOMapper implements Function<ShortURL, ShortURLDTO> {

    private Environment env;

    private final String DOMAIN_URL;

    public ShortURLDTOMapper() {
        var domain = System.getenv("DOMAIN_URL");
        if (domain == null) {
            this.DOMAIN_URL = "http://localhost:8080";
        } else {
            this.DOMAIN_URL = domain.trim().endsWith("/") ? domain.trim().substring(0, domain.length() - 1) : domain.trim();
        }
    }

    @Override
    public ShortURLDTO apply(ShortURL shortURL) {
        return new ShortURLDTO(
                shortURL.getId(),
                shortURL.getUrl(),
                this.DOMAIN_URL + "/r/" + shortURL.getHashCode(),
                shortURL.getHashCode(),
                shortURL.getHits(),
                shortURL.getCreated(),
                shortURL.getUpdated(),
                shortURL.getExpirationDate(),
                shortURL.getUser().getId()
        );
    }
}
