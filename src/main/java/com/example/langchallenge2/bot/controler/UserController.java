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

  public User addOneEmployee(User user) {
    Optional<User> existingUser = userRepository.findByChatId(user.getChatId());
    existingUser.ifPresent(userRepository::delete);
    return userRepository.save(user);
  }

  public void incrementScore(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setScore(existingUser.getScore() + 1);
      userRepository.save(existingUser);
    } else {
      userRepository.save(user);
    }
  }

  public User incrementDay(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setDayNumber(existingUser.getDayNumber() + 1);
      return userRepository.save(existingUser);
    } else {
      return userRepository.save(user);
    }
  }

  public int getScoreByChatId(long chatId) {
    Optional<User> userOptional = userRepository.findByChatId((int) chatId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user.getScore();
    } else {
      // Обробка ситуації, коли користувача не знайдено за вказаним chatId
      return -1; // Наприклад, повертаємо -1 або інше значення за замовчуванням
    }
  }

}



