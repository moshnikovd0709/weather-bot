package weatherBot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static weatherBot.Utils.*;

public class Bot extends TelegramLongPollingBot {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void setButtons(SendMessage sendMessage) {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        List<KeyboardRow> keyboardRowList = new ArrayList<>();
        KeyboardRow keyboardRow1 = new KeyboardRow();
        keyboardRow1.add(new KeyboardButton("/start"));
        keyboardRow1.add(new KeyboardButton("/info"));
        KeyboardRow keyboardRow2 = new KeyboardRow();
        keyboardRow2.add(new KeyboardButton("Petersburg"));
        keyboardRow2.add(new KeyboardButton("Moscow"));
        keyboardRow2.add(new KeyboardButton("Chicago"));
        KeyboardRow keyboardRow3 = new KeyboardRow();
        keyboardRow3.add(new KeyboardButton("Barcelona"));
        keyboardRow3.add(new KeyboardButton("Sochi"));
        keyboardRow3.add(new KeyboardButton("Rome"));

        keyboardRowList.add(keyboardRow1);
        keyboardRowList.add(keyboardRow2);
        keyboardRowList.add(keyboardRow3);
        replyKeyboardMarkup.setKeyboard(keyboardRowList);
    }

    public void sendMessage(Message message, String BotAnswer) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(message.getChatId().toString());
        sendMessage.setReplyToMessageId(message.getMessageId());
        sendMessage.setText(BotAnswer);
        try {
            setButtons(sendMessage);
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void onUpdateReceived(Update update) {
        Message message = update.getMessage();
        WeatherModel model = new WeatherModel();
        if (message != null) {
            switch (message.getText()) {
                case "/start":
                    sendMessage(message, "Hi, " + message.getFrom().getFirstName() + "\n"
                            + "Please click \"info\" to get more information about me");
                    break;
                case "/info":
                    sendMessage(message, "Enter a name of the place, where you want to know the weather" + "\n"
                            + "Or you can chose one from the list");
                    break;
                default:
                    try {
                        sendMessage(message, Weather.getWeather(message.getText(), model));
                    } catch (IOException e) {
                        sendMessage(message, "City was undefined");
                    }
            }
        }
    }

    public String getBotUsername() {
        return botName;
    }

    public String getBotToken() {
        return botToken;
    }
}
