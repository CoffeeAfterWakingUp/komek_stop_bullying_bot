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


@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class MenuHandler implements MessageHandler {

    @Lazy
    private final Bot bot;

    @Override
    public BotState getHandlerName() {
        return BotState.MENU;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        user.setStatus(UserBotStatus.MENU.toString());
        bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.menuText"),
                Buttons.threeButtons(properties.getProperty("bot.message.sendLetter"),
                        properties.getProperty("bot.message.appealInstruction"),
                        properties.getProperty("bot.message.changeLanguage")));
    }
}
