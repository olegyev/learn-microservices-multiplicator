package learn.microservices.multiplicator.user.controller;

import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{ids}")
    public ResponseEntity<List<User>> getUsersByIds(@PathVariable final List<String> ids) {
        log.info("Get user aliases by IDs {}", ids);
        return ResponseEntity.ok(userService.findAllByIdIn(ids));
    }

}