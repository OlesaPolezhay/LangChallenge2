package com.example.langchallenge2.bot.commands;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class StartCommandHandler extends BotCommand
{

  public StartCommandHandler(@Value("start") String commandIdentifier,@Value("") String description) {
    super(commandIdentifier, description);
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    String stickerWelcome = "CAACAgIAAxkBAAEEzJFmICZkvauEj6gZiDHm3a0-olqosQACuAADUomRI0m50GWI4c3YNAQ";

    String messageWelcome = "\uD83D\uDC4B Привіт " + chat.getFirstName() + "\n\n" +
        "Я - \uD83C\uDF0ELangChallenger \uD83C\uDF0E\n" +
        "\n" +
        "Проходь випробування, заробляй монети, та підвищуй свій рівень знань\uD83C\uDF0E\n" +
        "\n" +
        "Якщо потрібна буде допомога друкуй  /help.";

    try {
      InputFile sticker = new InputFile( stickerWelcome);
      absSender.execute(new SendSticker(chat.getId().toString(), sticker));

      absSender.execute(new SendMessage(chat.getId().toString(), messageWelcome));

    } catch (TelegramApiException e) {
      throw new RuntimeException(e);
    }
  }
}
