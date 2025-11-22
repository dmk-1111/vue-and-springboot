package com.dmk.practice.exercise_everyday.repositories;

import com.dmk.practice.exercise_everyday.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
