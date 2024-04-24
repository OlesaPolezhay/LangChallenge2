package com.example.langchallenge2.bot.commands;


import com.example.langchallenge2.bot.controler.UserController;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class QuizHandler extends BotCommand{
  private final UserController userController;

  public QuizHandler(@Value("start_quiz") String commandIdentifier,@Value("") String description,
      UserController userController) {
    super(commandIdentifier, description);
    this.userController = userController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    String stickerWelcome = "CAACAgIAAxkBAAEEzJFmICZkvauEj6gZiDHm3a0-olqosQACuAADUomRI0m50GWI4c3YNAQ";

    String messageWelcome = "почати випробування";

    try {
      InputFile sticker = new InputFile( stickerWelcome);
      absSender.execute(new SendSticker(chat.getId().toString(), sticker));

    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}

