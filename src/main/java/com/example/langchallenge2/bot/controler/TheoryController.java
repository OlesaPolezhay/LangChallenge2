package com.example.langchallenge2.bot.controler;

import com.example.langchallenge2.bot.repository.TheoryRepository;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TheoryController {

  private final TheoryRepository theoryRepository;

  public TheoryController(TheoryRepository theoryRepository) {
    this.theoryRepository = theoryRepository;
  }

  public void setTheory(Integer dayNumber, Integer msgNumber, String theory){
    theoryRepository.setTheory(dayNumber, theory, msgNumber);
  }
  public int checkData(Integer dayNumber, Integer msgNumber){
    return theoryRepository.checkData(dayNumber, msgNumber);
  }

}
