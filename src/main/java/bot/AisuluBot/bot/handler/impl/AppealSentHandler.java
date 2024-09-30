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

import static bot.AisuluBot.enums.BotState.APPEAL_SENT;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class AppealSentHandler implements MessageHandler {

    private final Bot bot;
    private final BotStateContext botStateContext;

    @Override
    public BotState getHandlerName() {
        return APPEAL_SENT;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals(properties.getProperty("bot.message.goBack"))) {
            botStateContext.processMessage(BotState.MENU, user, message, properties);
        } else {
            bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.errorInput"));
        }
    }
}
