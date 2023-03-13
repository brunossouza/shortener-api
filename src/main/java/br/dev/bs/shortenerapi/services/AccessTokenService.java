package br.dev.bs.shortenerapi.services;

import br.dev.bs.shortenerapi.mappers.AccessTokenMapper;
import br.dev.bs.shortenerapi.models.AccessToken;
import br.dev.bs.shortenerapi.models.User;
import br.dev.bs.shortenerapi.models.dtos.AccessTokenDTO;
import br.dev.bs.shortenerapi.repositories.AccessTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccessTokenService {

    private final AccessTokenMapper accessTokenMapper;

    private final AccessTokenRepository accessTokenRepository;

    private final UserService userService;

    public boolean isValid(String token) {
        var accessToken = accessTokenRepository.findByToken(token).orElseThrow(() -> new RuntimeException("Token not found"));
        return accessToken.getExpirationDate().after(new Date());
    }

    public List<AccessTokenDTO> getAllAccessToken() {
        try {
            var user = userService.getUserFromContext();
            return accessTokenRepository.findAllByUserId(user.getId()).orElseThrow(() -> new RuntimeException("Access token not found")).stream()
                    .map(accessTokenMapper)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Access token not found");
        }
    }

    // create access token
    public AccessTokenDTO createAccessToken(Date expirationDate) {
        var user = userService.getUserFromContext();
        var accessToken = AccessToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expirationDate(expirationDate)
                .build();
        return accessTokenMapper.apply(accessTokenRepository.save(accessToken));
    }

    public User getUserByAccessToken(String accessToken) {
        return accessTokenRepository.findByToken(accessToken).orElseThrow(() -> new RuntimeException("Token not found")).getUser();
    }
}
