package com.example.langchallenge2.bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
public class User{

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "chat_id", unique = true, nullable = false)
  @NotNull
  private Long chatId;

  @Column(name = "name", unique = true, nullable = false)
  @NotBlank
  private String name;

  @Column(name = "score", nullable = false)
  @NotNull
  private Integer score;

  @Column(name = "high_score", nullable = false)
  @NotNull
  private Integer highScore;

  @Column(name = "bot_state", nullable = false)
  @NotNull
  private String botState;

  @Column(name = "dayNumber")
  private Integer dayNumber;

  @Column(name = "questionNumber")
  private Integer questionNumber;

  public User(long chatId) {
    this.chatId = chatId;
    this.name = String.valueOf(chatId);
    this.score = 0;
    this.highScore = 0;
    this.botState = "start";
    this.dayNumber = 0;
    this.questionNumber = 0;
  }

  public User(long chatId, String name) {
    this.chatId = chatId;
    this.name = name;
    this.score = 0;
    this.highScore = 0;
    this.botState = "start";
    this.dayNumber = 0;
    this.questionNumber = 0;
  }

  public User() {

  }
  public void setScore(Integer score) {
    this.score = score;
  }

  public void setDayNumber(Integer dayNumber) {
    this.dayNumber = dayNumber;
  }

  public void setQuestionNumber(Integer questionNumber) {
    this.questionNumber = questionNumber;
  }
}

