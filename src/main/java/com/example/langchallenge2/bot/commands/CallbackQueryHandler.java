package com.example.langchallenge2.bot.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

//  не використовується

public class CallbackQueryHandler extends BotCommand {

  public void handleCallbackQuery(AbsSender absSender, CallbackQuery callbackQuery) {
    String data = callbackQuery.getData();
    Long chatId = callbackQuery.getMessage().getChatId();

    try {
      if ("/test_correct".equals(data)) {
        SendMessage message = new SendMessage(chatId.toString(), "Відповідь правильна!");
        absSender.execute(message);
      } else if ("/test_incorrect".equals(data)) {
        SendMessage message = new SendMessage(chatId.toString(), "Відповідь неправильна.");
        absSender.execute(message);
      }
    } catch (TelegramApiException e) {
      e.printStackTrace();
    }
  }
}
