package es.vortech.demo.controller;

import es.vortech.demo.dto.UserDto;
import es.vortech.demo.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping
    public List<UserDto> changePassword() {
        return userRepository.findAll()
                .stream()
                .map(user -> new UserDto(user.getName(), user.getEmail()))
                .toList();
    }
}
