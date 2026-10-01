package com.habitflow.service;

import com.habitflow.entity.Habit;
import com.habitflow.repository.HabitRepository;
import com.habitflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service @RequiredArgsConstructor
public class HabitService {
    private final HabitRepository habitRepository;
    private final UserRepository userRepository;

    public Habit create(Long userId, Habit habit) {
        var user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        habit.setId(null);
        habit.setUser(user);
        habit.setStreak(0);
        habit.setCreatedAt(LocalDate.now());
        return habitRepository.save(habit);
    }
    public List<Habit> getAll(Long userId) { return habitRepository.findByUserIdOrderByIdDesc(userId); }
    public Habit updateStreak(Long habitId) {
        Habit habit = habitRepository.findById(habitId).orElseThrow(() -> new IllegalArgumentException("Habit not found"));
        habit.setStreak(habit.getStreak() + 1);
        return habitRepository.save(habit);
    }
}
