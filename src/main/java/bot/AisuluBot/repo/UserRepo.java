package bot.AisuluBot.repo;

import bot.AisuluBot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {

    Optional<User> findFirstByChatId(String chatId);


}
