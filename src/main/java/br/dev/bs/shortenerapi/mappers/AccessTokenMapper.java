package br.dev.bs.shortenerapi.mappers;

import br.dev.bs.shortenerapi.models.AccessToken;
import br.dev.bs.shortenerapi.models.dtos.AccessTokenDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class AccessTokenMapper implements Function<AccessToken, AccessTokenDTO> {

    @Override
    public AccessTokenDTO apply(AccessToken accessToken) {
        return new AccessTokenDTO(
                accessToken.getToken(),
                accessToken.getUser().getId(),
                accessToken.getExpirationDate()
        );
    }
}
