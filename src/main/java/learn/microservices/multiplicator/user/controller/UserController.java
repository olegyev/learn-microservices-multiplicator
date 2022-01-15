package learn.microservices.multiplicator.user.controller;

import learn.microservices.multiplicator.user.entity.User;
import learn.microservices.multiplicator.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{idList}")
    public ResponseEntity<List<User>> getUsersByIdList(@PathVariable final List<String> idList) {
        return ResponseEntity.ok(userService.findAllByIdIn(idList));
    }

}