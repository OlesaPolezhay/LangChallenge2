package com.example.langchallenge2.bot.controler;

import com.example.langchallenge2.bot.model.User;
import com.example.langchallenge2.bot.repository.UserRepository;
import java.util.Optional;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {

  private final UserRepository userRepository;

  public UserController(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public void addOneEmployee(User user) {
    Optional<User> existingUser = userRepository.findByChatId(user.getChatId());
    existingUser.ifPresent(userRepository::delete);
    userRepository.save(user);
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

  public void resetScore(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setScore(0);
      userRepository.save(existingUser);
    } else {
      userRepository.save(user);
    }
  }

  public void resetQuestionNumber(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setQuestionNumber(0);
      userRepository.save(existingUser);
    } else {
      userRepository.save(user);
    }
  }

  public void incrementDay(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setDayNumber(existingUser.getDayNumber() + 1);
      existingUser.setQuestionNumber(0);
      existingUser.setScore(0);
      userRepository.save(existingUser);
    } else {
      userRepository.save(user);
    }
  }

  public void incrementQuestionNumber(User user) {
    Optional<User> existingUserOptional = userRepository.findByChatId(user.getChatId());
    if (existingUserOptional.isPresent()) {
      User existingUser = existingUserOptional.get();
      existingUser.setQuestionNumber(existingUser.getQuestionNumber() + 1);
      userRepository.save(existingUser);
    } else {
      userRepository.save(user);
    }
  }

  public int getQuestionNumberByChartIc(long chatId) {
    Optional<User> userOptional = userRepository.findByChatId(chatId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user.getQuestionNumber();
    } else {
      return -1;
    }
  }

  public int getScoreByChatId(long chatId) {
    Optional<User> userOptional = userRepository.findByChatId(chatId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user.getScore();
    } else {
      return -1;
    }
  }

  public int getDayByChatId(long chatId) {
    Optional<User> userOptional = userRepository.findByChatId(chatId);
    if (userOptional.isPresent()) {
      User user = userOptional.get();
      return user.getDayNumber();
    } else {
      return -1;
    }
  }
}



