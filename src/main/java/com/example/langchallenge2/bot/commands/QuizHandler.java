package com.example.langchallenge2.bot.commands;


import com.example.langchallenge2.bot.controler.UserController;
import com.example.langchallenge2.bot.message.MessageTest;
import com.example.langchallenge2.bot.model.Question;
import com.example.langchallenge2.bot.repository.QuestionRepository;
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
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class QuizHandler extends BotCommand {

  private final UserController userController;
  private final QuestionRepository questionRepository;

  public QuizHandler(@Value(MessageTest.StartCompetition) String commandIdentifier,
      @Value("") String description,
      UserController userController, QuestionRepository questionRepository) {
    super(commandIdentifier, description);
    this.userController = userController;
    this.questionRepository = questionRepository;
  }

  @Override
  public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {
    Question question = questionRepository.getRandomQuestion();
    //SendMessage message = new SendMessage(chat.getId().toString(), "ojfd");

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
            : "/test_incorrect"
    );
    rowInline1.add(inlineKeyboardButton1);

    List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
    inlineKeyboardButton2.setText(options.get(1));
    inlineKeyboardButton2.setCallbackData(
        Objects.equals(options.get(1), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect"
    );
    rowInline2.add(inlineKeyboardButton2);

    List<InlineKeyboardButton> rowInline3 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
    inlineKeyboardButton3.setText(options.get(2));
    inlineKeyboardButton3.setCallbackData(
        Objects.equals(options.get(2), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect"
    );
    rowInline3.add(inlineKeyboardButton3);

    List<InlineKeyboardButton> rowInline4 = new ArrayList<>();

    InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
    inlineKeyboardButton4.setText(options.get(3));
    inlineKeyboardButton4.setCallbackData(
        Objects.equals(options.get(3), question.getCorrectAnswer()) ? "/test_correct"
            : "/test_incorrect"
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
        Math.toIntExact(user.getId()), chat.getFirstName());

    userController.incrementScore(user1);

    execute(absSender, user, chat, strings);
  }

  public void incorrectAnswer(AbsSender absSender, User user, Chat chat, String[] strings)
      throws TelegramApiException {
    com.example.langchallenge2.bot.model.User user1 = new com.example.langchallenge2.bot.model.User(
        Math.toIntExact(user.getId()), chat.getFirstName());

    int score = userController.getScoreByChatId(user1.getChatId());

    String resultText = MessageTest.ResultMessage + score;

    InputFile sticker;
    if (score <= 5){
      sticker = new InputFile(MessageTest.StickerBadResult);
      resultText = resultText + MessageTest.MessageBadResult;
    } else if (score <= 10){
      sticker = new InputFile(MessageTest.StickerGoodResult);
      resultText = resultText + MessageTest.MessageGoodResult;
    }else if (score <= 15){
      sticker = new InputFile(MessageTest.StickerVeryGoodResult);
      resultText = resultText + MessageTest.MessageVeryGoodResult;
    }else {
      sticker = new InputFile(MessageTest.StickerAmazingResult);
      resultText = resultText + MessageTest.MessageAmazingResult;
    }

    absSender.execute(new SendMessage(chat.getId().toString(), resultText));
    absSender.execute(new SendSticker(chat.getId().toString(), sticker));
  }

}

