package com.ziya.moneymanagement.controller;

import com.ziya.moneymanagement.entity.User;
import com.ziya.moneymanagement.exception.UserNotFoundException;
import com.ziya.moneymanagement.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping("/current")
    public ResponseEntity<User> getActualUser() {
        return ResponseEntity.ok(service.getUserWithAuthorities().orElseThrow(UserNotFoundException::new));
    }

    @GetMapping
    public List<User> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getOne(@PathVariable Long id) {
        return service.getOne(id);
    }

    @GetMapping("/{email}")
    public User activate(@PathVariable("email") String email) {
        return service.activate(email);
    }

    @PostMapping
    public User save(@Valid @RequestBody User user) {
        return service.save(user);
    }

    @PutMapping
    public User update(@RequestBody User user) {
        return service.save(user);
    }

    @DeleteMapping("/{id}")
    public User deleteById(@PathVariable Long id) {
        return service.deleteById(id);
    }

}
