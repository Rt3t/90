package com.habitflow.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Habit {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    private int streak;
    private LocalDate createdAt;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private AppUser user;
}
