package bot.AisuluBot.service;

import bot.AisuluBot.entity.Group;
import bot.AisuluBot.repo.GroupRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupService {

    private final GroupRepo groupRepo;

    public List<Group> getAll() {
        return groupRepo.findAll();
    }

    public Group findByName(String name) {
        Optional<Group> groupOpt = groupRepo.findFirstByName(name);
        return groupOpt.orElse(null);
    }
}
