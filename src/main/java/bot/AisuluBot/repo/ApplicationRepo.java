package bot.AisuluBot.repo;

import bot.AisuluBot.entity.Application;
import bot.AisuluBot.entity.Group;
import bot.AisuluBot.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ApplicationRepo extends JpaRepository<Application, Long> {

    Optional<Application> findFirstByUserOrderByCreatedTimeDesc(User user);

    @Query(value = "select max(a.appId) from Application a where a.group=?1 order by a.createdTime desc")
    Long findLastAppIdByGroup(Group group);
}
