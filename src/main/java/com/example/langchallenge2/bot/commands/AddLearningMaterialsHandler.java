package com.example.langchallenge2.bot.commands;

import com.example.langchallenge2.bot.controler.TheoryController;
import com.example.langchallenge2.bot.message.MessageTest;
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

  public AddLearningMaterialsHandler(@Value("add_materials")String commandIdentifier,@Value("") String description,
      TheoryController theoryController, TheoryRepository theoryRepository,
      TheoryController theoryController1) {
    super(commandIdentifier, description);

    this.theoryController = theoryController1;
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

  public void addTheory (AbsSender absSender, User user, Chat chat, String message){

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
      }
    }
  }
}
