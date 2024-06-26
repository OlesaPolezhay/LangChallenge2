package com.example.langchallenge2.bot.repository;

import com.example.langchallenge2.bot.model.Question;
import com.example.langchallenge2.bot.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository  extends JpaRepository<User, Integer> {
  Optional<User> findByChatId(Integer chatId);



}