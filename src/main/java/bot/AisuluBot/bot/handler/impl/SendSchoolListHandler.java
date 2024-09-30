package bot.AisuluBot.bot.handler.impl;

import bot.AisuluBot.bot.Bot;
import bot.AisuluBot.bot.handler.MessageHandler;
import bot.AisuluBot.bot.utils.Buttons;
import bot.AisuluBot.entity.Group;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotState;
import bot.AisuluBot.enums.UserBotStatus;
import bot.AisuluBot.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

import static bot.AisuluBot.enums.BotState.SEND_SCHOOL_LIST;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class SendSchoolListHandler implements MessageHandler {

    @Lazy
    private final Bot bot;
    private final GroupService groupService;

    @Override
    public BotState getHandlerName() {
        return SEND_SCHOOL_LIST;
    }

    @Override
    public void handle(User user, Message message, Properties properties) {
        List<Group> groups = groupService.getAll();
        List<String> schools = groups.stream()
                .map(Group::getName)
                .collect(Collectors.toList());
        user.setStatus(UserBotStatus.CHOOSE_SCHOOL.toString());
        bot.sendMessage(message.getChatId().toString(), properties.getProperty("bot.message.chooseSchool"),
                Buttons.buttons(schools, properties.getProperty("bot.message.goBack")));
    }
}
