package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.BotStateContext;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.entity.Application;
import bot.AisuluBot.entity.Group;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.enums.DateFormat;
import bot.AisuluBot.enums.UserBotStatus;
import bot.AisuluBot.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import static bot.AisuluBot.enums.BotState.SEND_APPEAL_TO_GROUP;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SendAppealToGroup implements MessageHandler {

    @Lazy
    private final Bot bot;
    private final BotStateContext botStateContext;
    private final ApplicationService applicationService;

    private static final String GROUP_APPEAL_TEMPLATE = "<b>ЖАҢА ӨТІНІШ №%s</b>\n<b>Уақыт:</b> %s\n<b>Өтініштің мәтіні:</b>\n\n%s";

    @Override
    public BotState getHandlerName() {
        return SEND_APPEAL_TO_GROUP;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        if (message.getText().equals(properties.getProperty("bot.message.goBack"))) {
            botStateContext.processMessage(BotState.SEND_SCHOOL_LIST, user, message, properties);
        } else {
            String text = message.getText();
            if (!text.isEmpty()) {
                Application lastCreatedApplication = applicationService.getLastCreatedApplication(user);
                Group group = lastCreatedApplication.getGroup();
                Long nextAppId = applicationService.getNextAppId(group);
                lastCreatedApplication.setText(text);
                lastCreatedApplication.setSendTime(LocalDateTime.now());
                lastCreatedApplication.setAppId(nextAppId);
                applicationService.save(lastCreatedApplication);
                user.setStatus(UserBotStatus.APPEAL_SENT.toString());
                bot.sendMessage(group.getGroupId(), getAppealTemplate(properties, lastCreatedApplication));
                bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.textSent"));
            } else {
                bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.appealTextIsEmpty"));
            }
        }
    }

    private static String getAppealTemplate(Properties properties, Application application) {
        return GROUP_APPEAL_TEMPLATE
                .formatted(application.getAppId(),
                        application.getSendTime().format(DateTimeFormatter.ofPattern(DateFormat.DD_MM_YYYY_HH_MM.getValue())),
                        application.getText());
    }
}
