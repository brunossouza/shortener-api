package br.dev.bs.shortenerapi.controllers;

import br.dev.bs.shortenerapi.models.dtos.CreateUserDTO;
import br.dev.bs.shortenerapi.models.dtos.UserDTO;
import br.dev.bs.shortenerapi.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Secured({"ADMIN", "USER"})
    public ResponseEntity<UserDTO> getMe() {
        return ResponseEntity.ok(userService.getUserDTOFromContext());
    }

    @GetMapping
    // restrict access to admin
    @Secured({"ADMIN"})
    public ResponseEntity<List<UserDTO>> findAll() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    // restrict access to admin
    //@PreAuthorize("hasRole('ADMIN')")
    @Secured({"ADMIN"})
    public ResponseEntity findById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @PostMapping
    // restrict access to admin
    @Secured({"ADMIN"})
    public ResponseEntity<UserDTO> create(@RequestBody CreateUserDTO user) {
        try {
            return ResponseEntity.ok(userService.createUser(user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Secured({"ADMIN", "USER"})
    public ResponseEntity<UserDTO> update(@PathVariable String id, @RequestBody CreateUserDTO user) {
        if (userService.verifyIfUserIsNotTheSameLoggedOrAdmin(id))
            return ResponseEntity.badRequest().build();

        try {
            return ResponseEntity.ok(userService.updateUser(id, user));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    @Secured({"ADMIN", "USER"})
    public ResponseEntity delete(@PathVariable String id) {
        if (userService.verifyIfUserIsNotTheSameLoggedOrAdmin(id))
            return ResponseEntity.badRequest().build();

        try {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


}
