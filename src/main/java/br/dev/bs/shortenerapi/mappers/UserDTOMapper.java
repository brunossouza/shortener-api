package br.dev.bs.shortenerapi.mappers;

import br.dev.bs.shortenerapi.models.User;
import br.dev.bs.shortenerapi.models.dtos.UserDTO;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
public class UserDTOMapper implements Function<User, UserDTO> {

    @Override
    public UserDTO apply(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole().name()
        );
    }
}
