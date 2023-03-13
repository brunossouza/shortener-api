package br.dev.bs.shortenerapi.services;

import br.dev.bs.shortenerapi.enums.UsersRoles;
import br.dev.bs.shortenerapi.mappers.UserDTOMapper;
import br.dev.bs.shortenerapi.models.User;
import br.dev.bs.shortenerapi.models.dtos.CreateUserDTO;
import br.dev.bs.shortenerapi.models.dtos.UserDTO;
import br.dev.bs.shortenerapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserDTOMapper userDTOMapper;

    private final PasswordEncoder passwordEncoder;

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userDTOMapper)
                .collect(Collectors.toList());
    }

    public UserDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(userDTOMapper)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO getUserDTOFromContext() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username).map(userDTOMapper).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getUserFromContext() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserDTO createUser(CreateUserDTO createUserDTO) {
        var user = User.builder()
                .email(createUserDTO.email())
                .name(createUserDTO.name())
                .password(passwordEncoder.encode(createUserDTO.password()))
                .role(createUserDTO.role() == null ? UsersRoles.USER : UsersRoles.fromString(createUserDTO.role()))
                .build();

        return userDTOMapper.apply(userRepository.save(user));
    }

    public UserDTO updateUser(String id, CreateUserDTO user) {
        var userToUpdate = userRepository.findById(id).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        userToUpdate.setName(user.name());
        userToUpdate.setEmail(user.email());
        userToUpdate.setRole(user.role() == null ? UsersRoles.USER : UsersRoles.fromString(user.role()));

        if (user.password() != null) {
            userToUpdate.setPassword(passwordEncoder.encode(user.password()));
        }

        return userDTOMapper.apply(userRepository.save(userToUpdate));
    }

    public boolean verifyIfUserIsNotTheSameLoggedOrAdmin(String id) {
        var user = getUserFromContext();
        return !user.getId().equals(id) && !user.getRole().equals(UsersRoles.ADMIN);
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}
