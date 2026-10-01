package com.habitflow.repository;
import com.habitflow.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
public interface UserRepository extends JpaRepository<AppUser, Long> {}
