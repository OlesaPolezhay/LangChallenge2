package com.example.langchallenge2.bot.commands;


import com.example.langchallenge2.bot.controler.QuestionController;
import com.example.langchallenge2.bot.controler.UserController;
import com.example.langchallenge2.bot.message.MessageTest;
import com.example.langchallenge2.bot.model.Question;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class QuizHandler extends BotCommand {

  private final UserController userController;
  private final QuestionController questionController;

  public QuizHandler(@Value(MessageTest.StartCompetition) String commandIdentifier,
      @Value("") String description,
      UserController userController, QuestionController questionController) {
    super(commandIdentifier, description);
    this.userController = userController;
    this.questionController = questionController;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        user.getId(), chat.getFirstName());

    int day_number = userController.getDayByChatId(chat.getId());
    userController.incrementQuestionNumber(user1);
    int question_number = userController.getQuestionNumberByChartIc(chat.getId());
    int count_question = questionController.getCountQuestionInDay(day_number);

    if (question_number <= count_question) {
      Question question = questionController.getQuestionInDay(day_number, question_number);
      try {
        if (question.getSticker() != null) {
          InputFile sticker = new InputFile(question.getSticker());
          absSender.execute(new SendSticker(chat.getId().toString(), sticker));
        } else if (question.getPicture() != null) {

          InputFile photo = new InputFile(new File(question.getPicture()));
          SendPhoto sendPhoto = new SendPhoto(chat.getId().toString(), photo);
          absSender.execute(sendPhoto);
        }
        absSender.execute(sendQuestion(chat.getId().toString(), question));
      } catch (TelegramApiException e) {
        e.printStackTrace();
      }
    }
    else {
      try {
        printTheTestResult(absSender, user, chat);
      } catch (TelegramApiException e) {
        throw new RuntimeException(e);
      }
    }
  }

  public SendMessage sendQuestion(String chatId, Question question) {
    SendMessage message = new SendMessage();
    message.setChatId(chatId);
    message.setText(question.getQuestion());

    InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

    List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

    List<InlineKeyboardButton> rowInline1 = new ArrayList<>();

    // Створюємо список опцій та перемішуємо його
    List<String> options = new ArrayList<>();
    options.add(question.getCorrectAnswer());
    options.add(question.getOptionOne());
    options.add(question.getOptionTwo());
    options.add(question.getOptionThree());
    Collections.shuffle(options);

    InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
    inlineKeyboardButton1.setText(options.get(0));
    inlineKeyboardButton1.setCallbackData(
        Objects.equals(options.get(0), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect1"
    );
    rowInline1.add(inlineKeyboardButton1);

    List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText(options.get(1));
    inlineKeyboardButton2.setCallbackData(
        Objects.equals(options.get(1), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect2"
    );
    rowInline2.add(inlineKeyboardButton2);

    List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton3.setText(options.get(2));
    inlineKeyboardButton3.setCallbackData(
        Objects.equals(options.get(2), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect3"
    );
    rowInline3.add(inlineKeyboardButton3);

    List<InlineKeyboardButton> rowInline4 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
    inlineKeyboardButton4.setText(options.get(3));
    inlineKeyboardButton4.setCallbackData(
        Objects.equals(options.get(3), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect4"
    );
    rowInline4.add(inlineKeyboardButton4);

    rowsInline.add(rowInline1);
    rowsInline.add(rowInline2);
    rowsInline.add(rowInline3);
    rowsInline.add(rowInline4);

    markupInline.setKeyboard(rowsInline);
    message.setReplyMarkup(markupInline);

    return message;
  }

  public void correctAnswer(AbsSender absSender, User user, Chat chat, String[] strings) {
    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
      user.getId(), chat.getFirstName());

    userController.incrementScore(user1);

    execute(absSender, user, chat, strings);
  }

  public void incorrectAnswer(AbsSender absSender, User user, Chat chat, String[] strings)
      throws TelegramApiException {
    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        user.getId(), chat.getFirstName());

    int day_number = userController.getDayByChatId(chat.getId());
    int question_number = userController.getQuestionNumberByChartIc(chat.getId());

    Question question = questionController.getQuestionInDay(day_number, question_number);

    String correctAnswer =MessageTest.IncorrectAnswer + MessageTest.IncorrectAnswer2 + question.getCorrectAnswer();

    SendMessage sendMessage = new SendMessage(chat.getId().toString(),
        correctAnswer);
    absSender.execute(sendMessage);

    execute(absSender, user, chat, strings);
  }

  public void printTheTestResult(AbsSender absSender, User user, Chat chat)
      throws TelegramApiException {

    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        user.getId(), chat.getFirstName());

    int score = userController.getScoreByChatId(user1.getChatId());

    userController.resetScore(user1);
    userController.resetQuestionNumber(user1);

    String resultText = MessageTest.ResultMessage + score;

    InputFile sticker;
    if (score <= 2){
      sticker = new InputFile(MessageTest.StickerBadResult);
      resultText +=  MessageTest.MessageBadResult;
    } else if (score == 3){
      sticker = new InputFile(MessageTest.StickerGoodResult);
      resultText +=  MessageTest.MessageGoodResult;
    }else if (score == 4){
      sticker = new InputFile(MessageTest.StickerVeryGoodResult);
      resultText += MessageTest.MessageVeryGoodResult;
    }else {
      sticker = new InputFile(MessageTest.StickerAmazingResult);
      resultText += MessageTest.MessageAmazingResult;
    }


    absSender.execute(new SendMessage(chat.getId().toString(), resultText));
    absSender.execute(new SendSticker(chat.getId().toString(), sticker));

    navigateNextOrRetry(absSender, user, chat);

  }

   public void navigateNextOrRetry(AbsSender absSender, User user, Chat chat)
       throws TelegramApiException {

     SendMessage messageTheory;
     messageTheory = new SendMessage(chat.getId().toString(),
        MessageTest.MessageChooseButton);
     ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
     List<KeyboardRow> keyboard = new ArrayList<>();
     KeyboardRow row = new KeyboardRow();
     KeyboardButton button = new KeyboardButton(MessageTest.MessageTryAgain);
     KeyboardButton button2 = new KeyboardButton(MessageTest.MessageStartNewLesson);
     row.add(button);
     row.add(button2);
     keyboard.add(row);
     keyboardMarkup.setKeyboard(List.of(keyboard.toArray(new KeyboardRow[keyboard.size()])));
     messageTheory.setReplyMarkup(keyboardMarkup);
     absSender.execute(messageTheory);
   }

   public void resetTest (AbsSender absSender, User user, Chat chat, String[] strings){
     com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
         user.getId(), chat.getFirstName());
    userController.resetScore(user1);
    userController.resetQuestionNumber(user1);
    execute(absSender, user, chat, strings);
   }
}

