package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.bot.utils.Buttons;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.enums.UserBotStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Properties;

import static bot.AisuluBot.enums.BotState.SEND_LANGUAGE_OPTION;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SendLanguageOptionHandler implements MessageHandler {

    @Lazy
    private final Bot bot;

    @Override
    public BotState getHandlerName() {
        return SEND_LANGUAGE_OPTION;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        user.setStatus(UserBotStatus.CHOOSE_LANG.toString());
        bot.sendMessage(message.getChatId().toString(), properties.getProperty("start.text"),
                Buttons.twoButton(properties.getProperty("kz"), properties.getProperty("ru")));
    }
}
