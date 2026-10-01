package com.habitflow.controller;
import com.habitflow.entity.AppUser;
import com.habitflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController @RequestMapping("/api/users") @RequiredArgsConstructor
public class UserController {
    private final UserRepository users;
    @GetMapping("/{id}")
    public AppUser get(@PathVariable Long id) { return users.findById(id).orElseThrow(); }
    @GetMapping("/demo")
    public Map<String,Object> demo() { return Map.of("id", 1, "email", "demo@habitflow.local", "name", "Demo User"); }
}
