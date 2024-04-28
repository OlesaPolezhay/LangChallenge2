package com.example.langchallenge2.bot.repository;

import com.example.langchallenge2.bot.model.Question;
import com.example.langchallenge2.bot.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository <Question, Integer> {
  @Query(nativeQuery = true, value = "SELECT *  FROM lang_quiz ORDER BY random() LIMIT 1")
  Question getRandomQuestion();

  @Query(nativeQuery = true, value = "SELECT count(*) from lang_quiz where day_number = :dayNumber")
  Integer getCountQuestionInDay(@Param("dayNumber") int dayNumber);

  @Query(nativeQuery = true, value = "SELECT * from lang_quiz where day_number = :dayNumber AND question_number = :questionNumber")
  Question getQuestionInDay(@Param("dayNumber") int dayNumber,@Param("questionNumber") int questionNumber);

}
