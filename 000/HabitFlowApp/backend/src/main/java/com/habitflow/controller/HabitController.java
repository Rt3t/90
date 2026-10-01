package com.habitflow.controller;

import com.habitflow.entity.Habit;
import com.habitflow.service.HabitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/habits") @RequiredArgsConstructor
public class HabitController {
    private final HabitService habitService;

    @PostMapping
    public Habit create(@RequestParam Long userId, @RequestBody Habit habit) { return habitService.create(userId, habit); }
    @GetMapping("/{userId}")
    public List<Habit> getAll(@PathVariable Long userId) { return habitService.getAll(userId); }
    @PutMapping("/{habitId}/complete")
    public Habit complete(@PathVariable Long habitId) { return habitService.updateStreak(habitId); }
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String notFound(IllegalArgumentException e) { return e.getMessage(); }
}
