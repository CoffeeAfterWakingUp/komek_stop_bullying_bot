package bot.AisuluBot.service;

import bot.AisuluBot.entity.User;
import bot.AisuluBot.enums.BotLang;
import bot.AisuluBot.enums.UserBotStatus;
import bot.AisuluBot.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepo userRepo;

    public void save(User user) {
        if (user != null) {
            userRepo.save(user);
        }
    }

    public User getUser(Message message) {
        Optional<User> userOpt = userRepo.findFirstByChatId(message.getChatId().toString());
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setUsername(message.getFrom().getUserName());
            return user;
        } else {
            return createBotUser(message);
        }
    }

    private User createBotUser(Message message) {
        User user = User.builder()
                .username(message.getFrom().getUserName())
                .chatId(message.getChatId().toString())
                .status(UserBotStatus.START.toString())
                .registerAt(LocalDateTime.now())
                .lang(BotLang.RU.getValue())
                .build();
        return userRepo.save(user);
    }


}
