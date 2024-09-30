package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.io.File;
import java.util.Properties;

import static bot.AisuluBot.enums.BotState.SEND_INSTRUCTION;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SendInstructionHandler implements MessageHandler {

    @Lazy
    private final Bot bot;

    @Value("${file.bot-instruction}")
    private String file;

    @Override
    public BotState getHandlerName() {
        return SEND_INSTRUCTION;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.instructionSending"));
        bot.sendDocument(message.getChatId().toString(), new File(file));
    }
}
