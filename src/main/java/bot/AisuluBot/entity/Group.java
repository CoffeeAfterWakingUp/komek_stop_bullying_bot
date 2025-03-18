package bot.AisuluBot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tg_group")
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private String groupId;

    @OneToMany(
            mappedBy = "group",
            cascade = CascadeType.ALL
    )
    private List<Application> applications = new ArrayList<>();
}
