package br.dev.bs.shortenerapi.services;

import br.dev.bs.shortenerapi.config.jwt.JwtValideteService;
import br.dev.bs.shortenerapi.enums.UsersRoles;
import br.dev.bs.shortenerapi.models.User;
import br.dev.bs.shortenerapi.models.dtos.AuthenticationRequest;
import br.dev.bs.shortenerapi.models.dtos.AuthenticationResponse;
import br.dev.bs.shortenerapi.models.dtos.RegisterRequest;
import br.dev.bs.shortenerapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticateService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtValideteService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var user = User
                .builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(UsersRoles.USER)
                .build();
        userRepository.save(user);

        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        var user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        var token = jwtService.generateToken(user);

        return AuthenticationResponse.builder().token(token).build();
    }
}
