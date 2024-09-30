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
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.Properties;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SendLetterHandler implements MessageHandler {

    @Lazy
    private final Bot bot;
    private final GroupService groupService;
    private final ApplicationService applicationService;
    private final BotStateContext botStateContext;

    @Override
    public BotState getHandlerName() {
        return BotState.SEND_LETTER;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals(properties.getProperty("bot.message.goBack"))) {
            botStateContext.processMessage(BotState.MENU, user, message, properties);
        } else {
            String schoolName = message.getText();
            Group group = groupService.findByName(schoolName);
            if (group != null) {
                Application application = new Application();
                application.setCreatedTime(LocalDateTime.now());
                application.setUser(user);
                application.setGroup(group);
                applicationService.save(application);

                user.setStatus(UserBotStatus.SEND_LETTER.toString());
                bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.writeText"),
                        Buttons.oneButton(properties.getProperty("bot.message.goBack")));
            } else {
                bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.chosenSchoolIsNotOnList"));
            }
        }
    }
}
