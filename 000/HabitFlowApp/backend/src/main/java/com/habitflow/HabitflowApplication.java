package com.habitflow;

import com.habitflow.entity.AppUser;
import com.habitflow.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HabitflowApplication {
    public static void main(String[] args) { SpringApplication.run(HabitflowApplication.class, args); }

    @Bean
    CommandLineRunner seed(UserRepository users) {
        return args -> {
            if (users.findById(1L).isEmpty()) {
                users.save(AppUser.builder().email("demo@habitflow.local").name("Demo User").password("demo").build());
            }
        };
    }
}
