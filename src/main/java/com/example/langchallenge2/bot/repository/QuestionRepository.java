package com.example.langchallenge2.bot.repository;

import com.example.langchallenge2.bot.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository <Question, Integer> {
  @Query(nativeQuery = true, value = "SELECT *  FROM lang_quiz ORDER BY random() LIMIT 1")
  Question getRandomQuestion();
}
