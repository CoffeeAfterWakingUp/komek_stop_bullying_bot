package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.BotStateContext;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.bot.utils.Buttons;
import bot.AisuluBot.entity.Application;
import bot.AisuluBot.entity.Group;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.enums.UserBotStatus;
import bot.AisuluBot.service.ApplicationService;
import bot.AisuluBot.service.GroupService;
import bot.AisuluBot.utils.PropertiesReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.Properties;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MainHandler implements MessageHandler {

    private final Bot bot;
    private final BotStateContext botStateContext;

    @Override
    public BotState getHandlerName() {
        return BotState.MAIN;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals("/start") || user.getStatus().equals(UserBotStatus.START.toString())) {
            botStateContext.processMessage(BotState.SEND_LANGUAGE_OPTION, user, message, properties);
        } else if (user.getStatus().equals(UserBotStatus.CHOOSE_LANG.toString())) {
            botStateContext.processMessage(BotState.CHOOSE_LANG, user, message, properties);
            properties = PropertiesReader.getProperties(user.getLang());
            botStateContext.processMessage(BotState.MENU, user, message, properties);
        } else if (user.getStatus().equals(UserBotStatus.MENU.toString())) {
            botStateContext.processMessage(BotState.MENU_OPTION, user, message, properties);
        } else if (user.getStatus().equals(UserBotStatus.CHOOSE_SCHOOL.toString())) {
            botStateContext.processMessage(BotState.SEND_LETTER, user, message, properties);
        } else if (user.getStatus().equals(UserBotStatus.SEND_LETTER.toString())) {
            botStateContext.processMessage(BotState.SEND_APPEAL_TO_GROUP, user, message, properties);
        } else if (user.getStatus().equals(UserBotStatus.APPEAL_SENT.toString())) {
            botStateContext.processMessage(BotState.APPEAL_SENT, user, message, properties);
        } else {
            bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.errorInput"));
        }
    }


}
