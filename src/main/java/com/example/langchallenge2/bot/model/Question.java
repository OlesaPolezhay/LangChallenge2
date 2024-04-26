package com.example.langchallenge2.bot.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Entity
@Table(name = "lang_quiz")
@Getter
public class Question {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "question", nullable = false)
  @NotBlank
  private String question;

  @Column(name = "picture")
  @NotBlank
  private String picture;

  @Column(name = "sticker")
  @NotBlank
  private String sticker;

  @Column(name = "answer_correct", nullable = false)
  @NotBlank
  private String correctAnswer;

  @Column(name = "option2", nullable = false)
  @NotBlank
  private String optionOne;

  @Column(name = "option1", nullable = false)
  @NotBlank
  private String optionTwo;

  @Column(name = "option3", nullable = false)
  @NotBlank
  private String optionThree;

  @Override
  public String toString() {
    return "Question{" +
        "id=" + id +
        ", question='" + question + '\'' +
        ", picture='" + picture + '\'' +
        ", sticker='" + sticker + '\'' +
        ", correctAnswer='" + correctAnswer + '\'' +
        ", optionOne='" + optionOne + '\'' +
        ", optionTwo='" + optionTwo + '\'' +
        ", optionThree='" + optionThree + '\'' +
        '}';
  }

  public void question (Question question){
    this.question = question.getQuestion();

  }
}
