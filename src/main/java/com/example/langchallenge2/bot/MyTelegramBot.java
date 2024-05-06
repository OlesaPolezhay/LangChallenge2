package com.example.langchallenge2.bot;

import com.example.langchallenge2.bot.commands.AddLearningMaterialsHandler;
import com.example.langchallenge2.bot.commands.QuizHandler;
import com.example.langchallenge2.bot.commands.StartLessonHandler;
import com.example.langchallenge2.bot.message.MessageTest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class MyTelegramBot extends TelegramLongPollingCommandBot {

  private final String username;
  @Autowired
  private QuizHandler quizHandler;
  @Autowired
  private StartLessonHandler startLessonHandler;

  @Autowired
  private AddLearningMaterialsHandler addLearningMaterialsHandler;


  public MyTelegramBot(@Value("${bot.token}") String botToken,
      @Value("${bot.username}") String username) {
    super(botToken);
    this.username = username;
  }

  @Override
  public String getBotUsername() {
    return username;
  }

  @Override
  public void processNonCommandUpdate(Update update) {
    if (update.hasMessage() && update.getMessage().hasText()) {
      String messageText = update.getMessage().getText();
      if(messageText.equals(MessageTest.MessageButtonStartTest)){
        quizHandler.execute(this, update.getMessage().getFrom() ,update.getMessage().getChat(), null);
      }
      if(messageText.equals(MessageTest.MessageButtonStartLesson)){
       startLessonHandler.execute(this, update.getMessage().getFrom() ,update.getMessage().getChat(), null);
      }
      if(messageText.equals(MessageTest.MessageTryAgain)){
        quizHandler.resetTest(this, update.getMessage().getFrom() ,update.getMessage().getChat(), null);
      }
      if(messageText.equals(MessageTest.MessageStartNewLesson)){
        startLessonHandler.execute(this, update.getMessage().getFrom() ,update.getMessage().getChat(), null);
      }
      if(messageText.contains("theory")){
        addLearningMaterialsHandler.addTheory(this, update.getMessage().getFrom() ,update.getMessage().getChat(), messageText);
      }
    }
    if (update.hasCallbackQuery()) {
      CallbackQuery callbackQuery = update.getCallbackQuery();
      String callbackData = callbackQuery.getData();
      Long chatId = callbackQuery.getMessage().getChatId();

      try {
        if ("/test_correct".equals(callbackData)) {
          execute(new SendMessage(chatId.toString(), MessageTest.CorrectAnswer));
          Thread.sleep(1000);
          quizHandler.correctAnswer(this, callbackQuery.getFrom(),
              callbackQuery.getMessage().getChat(), null);
        } else if ("/test_incorrect1".equals(callbackData) || "/test_incorrect2".equals(callbackData)
        || "/test_incorrect3".equals(callbackData) || "/test_incorrect4".equals(callbackData)) {
          quizHandler.incorrectAnswer(this, callbackQuery.getFrom(),
              callbackQuery.getMessage().getChat(), null);
        }
      } catch (TelegramApiException | InterruptedException e) {
        e.printStackTrace();
      }
    }
  }

  @Override
  public void onUpdatesReceived(List<Update> updates) {
    super.onUpdatesReceived(updates);
  }

}
