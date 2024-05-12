package com.example.langchallenge2.bot.commands;

import com.example.langchallenge2.bot.controler.QuestionController;
import com.example.langchallenge2.bot.controler.TheoryController;
import com.example.langchallenge2.bot.message.MessageTest;
import com.example.langchallenge2.bot.model.Question;
import com.example.langchallenge2.bot.repository.QuestionRepository;
import com.example.langchallenge2.bot.repository.TheoryRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class AddLearningMaterialsHandler extends BotCommand {


  private final TheoryController theoryController;
  private final QuestionController questionController;

  public AddLearningMaterialsHandler(@Value("add_materials") String commandIdentifier,
      @Value("") String description,
      TheoryController theoryController, TheoryRepository theoryRepository,
      TheoryController theoryController1, QuestionRepository questionRepository,
      QuestionController questionController) {
    super(commandIdentifier, description);

    this.theoryController = theoryController1;
    this.questionController = questionController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    if (strings.length == 0) {
      SendMessage message = new SendMessage(chat.getId().toString(),MessageTest.MessageAddMaterials);
      try {
        absSender.execute(message);
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
  }

  public void addTheory (AbsSender absSender, User user, Chat chat, String message)
      throws TelegramApiException {

    String[] parts = message.split(",\n");

    int dayNumber = 0;
    int msgNumber = 0;
    String theory = "";

    for (String part : parts) {
      part = part.trim();

      String[] keyValue = part.split(":");
      String key = keyValue[0].trim();
      String value = keyValue[1].trim();

      switch (key) {
        case "daynumber" -> dayNumber = Integer.parseInt(value);
        case "msgnumber" -> msgNumber = Integer.parseInt(value);
        case "theory" -> theory = value.replaceAll("\"", "");
      }
    }

    if (dayNumber != 0 || msgNumber != 0 || theory.isEmpty() ) {
      if (theoryController.checkData(dayNumber, msgNumber) == 0) {
        theoryController.setTheory(dayNumber, msgNumber, theory);
        absSender.execute(
            new SendMessage(chat.getId().toString(), MessageTest.MessageAddTheorySuccess));
      }else
        absSender.execute(new SendMessage(chat.getId().toString(), MessageTest.MessageAddTheoryError));
    }
    else absSender.execute(new SendMessage(chat.getId().toString(), MessageTest.MessageAddTheoryError2));
  }

  public void addTest (AbsSender absSender, User user, Chat chat, String message)
      throws TelegramApiException {

    String question = "";
    String sticker = "";
    String answerCorrect = "";
    String option1 = "";
    String option2 = "";
    String option3 = "";
    int dayNumber = 0;
    int questionNumber = 0;

    String[] parts = message.split(";\s*");
    for (String part : parts) {
      String[] keyValue = part.split(":");
      if (keyValue.length != 2) {
        continue;
      }
      String key = keyValue[0].trim();
      String value = keyValue[1].trim();

      switch (key) {
        case "\"question\"":
          question = value.replaceAll("\"", "");
          break;
        case "\"sticker\"":
          sticker = value.replaceAll("\"", "");
          break;
        case "\"answer_correct\"":
          answerCorrect = value.replaceAll("\"", "");
          break;
        case "\"option1\"":
          option1 = value.replaceAll("\"", "");
          break;
        case "\"option2\"":
          option2 = value.replaceAll("\"", "");
          break;
        case "\"option3\"":
          option3 = value.replaceAll("\"", "");
          break;
        case "\"day_number\"":
          dayNumber = Integer.parseInt(value.replaceAll("\"", ""));
          break;
        case "\"question_number\"":
          questionNumber = Integer.parseInt(value.replaceAll("\"", ""));
          break;
      }
    }

    Question test = new Question(question, sticker, answerCorrect, option1, option2, option3,
        dayNumber, questionNumber);
    if (questionController.checkQuestion(dayNumber, questionNumber) != 1) {
      questionController.setQuestion(test);
      absSender.execute(
          new SendMessage(chat.getId().toString(), MessageTest.MessageAddTheorySuccess));
    } else {
      absSender.execute(
          new SendMessage(chat.getId().toString(), MessageTest.MessageAddTheoryError));
    }
  }
}
