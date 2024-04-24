package com.example.langchallenge2.bot.controler;

import com.example.langchallenge2.bot.model.User;
import com.example.langchallenge2.bot.repository.UserRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {
  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/users")
  public Iterable<User> findAllEmployees() {
    return this.userRepository.findAll();
  }


  public User addOneEmployee(User user) {
    Optional<User> existingUser = userRepository.findByChatId(user.getChatId());
    return existingUser.orElseGet(() -> userRepository.save(user));
  }


}

