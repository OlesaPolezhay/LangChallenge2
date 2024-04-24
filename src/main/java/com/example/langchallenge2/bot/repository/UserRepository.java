package com.example.langchallenge2.bot.repository;

import com.example.langchallenge2.bot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {

}