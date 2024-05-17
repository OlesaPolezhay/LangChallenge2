package com.example.langchallenge2.bot.commands;

import com.example.langchallenge2.bot.controler.UserController;
import com.example.langchallenge2.bot.message.MessageTest;
import com.example.langchallenge2.bot.repository.UserRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommandHandler extends BotCommand
{
  private final UserController userController;

  public StartCommandHandler(@Value("start") String commandIdentifier,@Value("") String description,
      UserController userController, UserRepository userRepository) {
    super(commandIdentifier, description);
    this.userController = userController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

   com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
       user.getId(), chat.getFirstName());

   userController.addOneEmployee(user1);

    String messageWelcome = MessageTest.WelcomeMessage1 + chat.getFirstName() + "\n\n" +
        MessageTest.WelcomeMessage2;

    SendMessage sendMessageWelcome = new SendMessage(chat.getId().toString(), messageWelcome);

    ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
    List<KeyboardRow> keyboard = new ArrayList<>();
    KeyboardRow row = new KeyboardRow();
    KeyboardButton button = new KeyboardButton(MessageTest.MessageButtonStartLesson);
    keyboardMarkup.setResizeKeyboard(true);
    row.add(button);
    keyboard.add(row);

    keyboardMarkup.setKeyboard(keyboard);
    //keyboardMarkup.setOneTimeKeyboard(true);
    sendMessageWelcome.setReplyMarkup(keyboardMarkup);

    try {
      InputFile sticker = new InputFile( MessageTest.StickerWelcome);
      absSender.execute(new SendSticker(chat.getId().toString(), sticker));
      absSender.execute(sendMessageWelcome);

    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
