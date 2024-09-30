package bot.AisuluBot.bot;

import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.service.UserService;
import bot.AisuluBot.utils.PropertiesReader;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.File;
import java.util.Properties;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    private final UserService userService;
    private final BotStateContext botStateContext;

    public Bot(@Value("${bot.token}") String botToken, UserService userService, BotStateContext botStateContext) {
        super(botToken);
        this.userService = userService;
        this.botStateContext = botStateContext;
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @PostConstruct
    private void init() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.isUserMessage()) {
                User user = userService.getUser(message);
                Properties properties = PropertiesReader.getProperties(user.getLang());
                if (message.hasText()) {
                    log.info("{}({}) : {}. Status: {}", user.getUsername(), user.getChatId(), message.getText(), user.getStatus());
                    botStateContext.processMessage(BotState.MAIN, user, message, properties);
                }
                userService.save(user);
            }
        }
    }

    public Message sendMessage(String chatId, String text) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .parseMode(ParseMode.HTML)
                .build();
        return sendMessage(sendMessage);
    }

    public void sendMessage(String chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .text(text)
                .replyMarkup(replyKeyboard)
                .parseMode(ParseMode.HTML)
                .build();
        sendMessage(sendMessage);
    }

    public void sendDocument(String chatId, File file) {
        SendDocument sendDocument = SendDocument.builder()
                .chatId(chatId)
                .document(new InputFile(file))
                .build();
        sendDocument(sendDocument);
    }

    public Message sendMessage(SendMessage sendMessage) {
        try {
            return execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("sendMessage(SendMessage sendMessage) ex: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public Message sendDocument(SendDocument sendDocument) {
        try {
            return execute(sendDocument);
        } catch (TelegramApiException e) {
            log.error("sendMessage(SendMessage sendMessage) ex: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
