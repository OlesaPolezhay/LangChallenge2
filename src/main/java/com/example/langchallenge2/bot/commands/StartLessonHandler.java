package com.example.langchallenge2.bot.commands;

import com.example.langchallenge2.bot.controler.QuestionController;
import com.example.langchallenge2.bot.controler.UserController;
import com.example.langchallenge2.bot.message.MessageTest;
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

  public StartLessonHandler(@Value(MessageTest.StartLesson) String commandIdentifier, @Value("") String description,
      UserController userController, QuestionController questionController) {
    super(commandIdentifier, description);
    this.userController = userController;
    this.questionController = questionController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    SendMessage messageTheory;

    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        user.getId(), chat.getFirstName());
    userController.incrementDay(user1);

    int lesson = userController.getDayByChatId(chat.getId());

    if(questionController.getCountQuestionInDay(lesson) != 0) {
      SendMessage sendMessage = new SendMessage(chat.getId().toString(), MessageTest.MessageTheoryForTheFirstLesson);
      sendMessage.enableHtml(true);
      SendMessage sendMessage2 = new SendMessage(chat.getId().toString(), MessageTest.MessageTheoryForTheFirstLesson2);
      sendMessage2.enableHtml(true);
      try {
        absSender.execute(sendMessage);
        Thread.sleep(3000);
        absSender.execute(sendMessage2);
      } catch (TelegramApiException | InterruptedException e) {
        throw new RuntimeException(e);
      }

      messageTheory = new SendMessage(chat.getId().toString(),
          MessageTest.MessageStartTest);
      ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
      List<KeyboardRow> keyboard = new ArrayList<>();
      KeyboardRow row = new KeyboardRow();
      KeyboardButton button = new KeyboardButton(MessageTest.MessageButtonStartTest);
      row.add(button);
      keyboard.add(row);
      keyboardMarkup.setKeyboard(List.of(keyboard.toArray(new KeyboardRow[keyboard.size()])));
      messageTheory.setReplyMarkup(keyboardMarkup);
    }
    else{
      messageTheory = new SendMessage(chat.getId().toString(), MessageTest.MessageNotAvailableLesson);
    }
      try {
        absSender.execute(messageTheory);
      } catch (TelegramApiException e) {
        throw new RuntimeException(e);
      }

  }
}
