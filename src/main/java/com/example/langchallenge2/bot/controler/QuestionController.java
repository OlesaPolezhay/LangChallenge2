package com.example.langchallenge2.bot.controler;

import com.example.langchallenge2.bot.model.Question;
import com.example.langchallenge2.bot.repository.QuestionRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class QuestionController {
  private final QuestionRepository questionRepository;

  public QuestionController(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Question getRandomQuestion(){
   return questionRepository.getRandomQuestion();
  }
}
