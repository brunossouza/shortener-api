package br.dev.bs.shortenerapi.services;

import br.dev.bs.shortenerapi.enums.CreatedBy;
import br.dev.bs.shortenerapi.enums.UsersRoles;
import br.dev.bs.shortenerapi.mappers.ShortURLDTOMapper;
import br.dev.bs.shortenerapi.models.ShortURL;
import br.dev.bs.shortenerapi.models.User;
import br.dev.bs.shortenerapi.models.dtos.ShortURLDTO;
import br.dev.bs.shortenerapi.repositories.ShortURLRepository;
import br.dev.bs.shortenerapi.utils.ShortenerUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShortURLService {

    private final ShortURLRepository repository;

    private final UserService userService;

    private final AccessTokenService accessTokenService;

    private final ShortURLDTOMapper shortURLDTOMapper;

    public List<ShortURLDTO> getAllURLs() {
        var user = userService.getUserFromContext();

        // If user is admin, return all URLs
        if (user.getRole().equals(UsersRoles.ADMIN.name()))
            return repository.findAll()
                    .stream()
                    .map(shortURLDTOMapper)
                    .collect(Collectors.toList());


        // If user is not admin, return only his URLs
        return repository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("URLs not found"))
                .stream()
                .map(shortURLDTOMapper)
                .collect(Collectors.toList());
    }

    public ShortURLDTO getById(String id) {
        var shortURL = repository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
        return shortURLDTOMapper.apply(shortURL);
    }

    public ShortURL getURLByHashCode(String hashCode) {
        return repository.findByHashCode(hashCode).orElse(null);
    }

    public ShortURLDTO createURL(ShortURLDTO shortDTO) {
        var user = userService.getUserFromContext();
        return saveURL(shortDTO, user, CreatedBy.USER);
    }

    public ShortURLDTO createURLApplication(String accessToken, ShortURLDTO shortDTO) {
        var user = accessTokenService.getUserByAccessToken(accessToken);
        return saveURL(shortDTO, user, CreatedBy.APPLICATION);
    }

    private ShortURLDTO saveURL(ShortURLDTO shortDTO, User user, CreatedBy createdBy) {
        var shortURL = ShortURL.builder()
                .url(shortDTO.url())
                .expirationDate(shortDTO.expires_in())
                .user(user)
                .createdBy(createdBy)
                .build();

        shortURL.setHashCode(ShortenerUtils.generateHashCode(shortURL.getUrl()));

        return shortURLDTOMapper.apply(repository.save(shortURL));
    }

    public void incrementHits(ShortURL shortURL) {
        shortURL.setHits(shortURL.getHits() + 1);
        repository.save(shortURL);
    }

    public void deleteURL(String id) {
        var shortURL = repository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));
        repository.delete(shortURL);
    }

    public ShortURLDTO updateURL(String id, ShortURLDTO shortURL) {
        var user = userService.getUserFromContext();
        var shortURLDB = repository.findById(id).orElseThrow(() -> new RuntimeException("id not found"));

        if (!shortURLDB.getUser().getId().equals(user.getId()) && !user.getRole().equals(UsersRoles.ADMIN.name()))
            throw new RuntimeException("You can't update this URL");

        shortURLDB.setUrl(shortURL.url());
        shortURLDB.setExpirationDate(shortURL.expires_in());

        return shortURLDTOMapper.apply(repository.save(shortURLDB));
    }

    public boolean isExpired(ShortURL shortURL) {
        if (shortURL.getExpirationDate() == null)
            return false;

        return Calendar.getInstance().getTime().after(shortURL.getExpirationDate());
    }
}
