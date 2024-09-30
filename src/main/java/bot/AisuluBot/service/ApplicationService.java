package bot.AisuluBot.service;

import bot.AisuluBot.entity.Application;
import bot.AisuluBot.entity.Group;
import bot.AisuluBot.entity.User;
import bot.AisuluBot.repo.ApplicationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepo applicationRepo;


    public void save(Application application) {
        applicationRepo.save(application);
    }

    public Application getLastCreatedApplication(User user) {
        return applicationRepo.findFirstByUserOrderByCreatedTimeDesc(user).orElse(null);
    }

    public Long getNextAppId(Group group) {
        Long lastAppIdByGroup = applicationRepo.findLastAppIdByGroup(group);
        return lastAppIdByGroup != null && lastAppIdByGroup != 0 ? lastAppIdByGroup + 1 : 1;
    }
}
