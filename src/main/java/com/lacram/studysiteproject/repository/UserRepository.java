package com.lacram.studysiteproject.repository;

import com.lacram.studysiteproject.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

}
