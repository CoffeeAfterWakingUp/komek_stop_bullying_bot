package bot.AisuluBot.repo;

import bot.AisuluBot.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GroupRepo extends JpaRepository<Group, Long> {

    Optional<Group> findFirstByName(String name);
}
