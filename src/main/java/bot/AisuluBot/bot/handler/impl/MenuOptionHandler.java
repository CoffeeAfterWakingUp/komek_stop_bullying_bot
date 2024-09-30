package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.BotStateContext;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Properties;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MenuOptionHandler implements MessageHandler {


    @Lazy
    private final Bot bot;
    private final BotStateContext botStateContext;

    @Override
    public BotState getHandlerName() {
        return BotState.MENU_OPTION;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals(properties.getProperty("bot.message.sendLetter"))) {
            botStateContext.processMessage(BotState.SEND_SCHOOL_LIST, user, message, properties);
        } else if (message.getText().equals(properties.getProperty("bot.message.changeLanguage"))) {
            botStateContext.processMessage(BotState.SEND_LANGUAGE_OPTION, user, message, properties);
        } else if (message.getText().equals(properties.getProperty("bot.message.appealInstruction"))) {
            botStateContext.processMessage(BotState.SEND_INSTRUCTION, user, message, properties);
        } else {
            bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.errorInput"));
        }
    }
}
