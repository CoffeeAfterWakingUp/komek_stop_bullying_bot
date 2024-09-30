package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotLang;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.utils.PropertiesReader;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.Properties;

import static bot.AisuluBot.enums.BotState.CHOOSE_LANG;

@Component
public class ChooseLangHandler implements MessageHandler {

    @Override
    public BotState getHandlerName() {
        return CHOOSE_LANG;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals(properties.getProperty("ru"))) {
            user.setLang(BotLang.RU.getValue());
        } else {
            user.setLang(BotLang.KZ.getValue());
        }
    }
}
