package bot.AisuluBot.bot.handler;

import bot.AisuluBot.entity.User;
import org.telegram.telegrambots.meta.api.objects.Message;


import java.util.Properties;

public interface MessageHandler extends Handler {

    void handle(User user, Message message, Properties properties);
}
