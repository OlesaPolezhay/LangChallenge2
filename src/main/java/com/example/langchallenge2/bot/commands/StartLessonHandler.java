package com.example.langchallenge2.bot.commands;

import com.example.langchallenge2.bot.controler.QuestionController;
import com.example.langchallenge2.bot.controler.TheoryController;
import com.example.langchallenge2.bot.controler.UserController;
import com.example.langchallenge2.bot.message.MessageTest;
import com.example.langchallenge2.bot.model.Theory;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class StartLessonHandler extends BotCommand {

  private final UserController userController;
  private final QuestionController questionController;
  private final TheoryController theoryController;

  public StartLessonHandler(@Value(MessageTest.StartLesson) String commandIdentifier,
      @Value("") String description,
      UserController userController, QuestionController questionController,
      TheoryController theoryController) {
    super(commandIdentifier, description);
    this.userController = userController;
    this.questionController = questionController;
    this.theoryController = theoryController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    SendMessage messageTheory;

    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        user.getId(), chat.getFirstName());

    int lesson = userController.getDayByChatId(chat.getId());

    if (questionController.getCountQuestionInDay(lesson + 1) != 0) {
      userController.incrementDay(user1);
      lesson = userController.getDayByChatId(chat.getId());
      System.out.println(lesson);
      int i = 1;
      System.out.println(theoryController.checkData(lesson, i));
      while (theoryController.checkData(lesson, i) == 1) {
        Theory theory = theoryController.getQuestionInDay(lesson, i);
        SendMessage sendMessage = new SendMessage(chat.getId().toString(),
            theory.getTheory());
        sendMessage.enableHtml(true);
        try {
          absSender.execute(sendMessage);
          Thread.sleep(1000);
        } catch (TelegramApiException | InterruptedException e) {
          throw new RuntimeException(e);
        }
        i++;
      }
      messageTheory = new SendMessage(chat.getId().toString(),
          MessageTest.MessageStartTest);
      ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
      List<KeyboardRow> keyboard = new ArrayList<>();
      KeyboardRow row = new KeyboardRow();
      KeyboardButton button = new KeyboardButton(MessageTest.MessageButtonStartTest);
      keyboardMarkup.setResizeKeyboard(true);
      row.add(button);
      keyboard.add(row);
      keyboardMarkup.setKeyboard(keyboard);
      keyboardMarkup.setOneTimeKeyboard(true);
      messageTheory.setReplyMarkup(keyboardMarkup);
    } else {
      messageTheory = new SendMessage(chat.getId().toString(),
          MessageTest.MessageNotAvailableLesson);
    }
    try {
      messageTheory.enableHtml(true);
      absSender.execute(messageTheory);
    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
