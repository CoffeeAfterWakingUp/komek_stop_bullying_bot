package bot.AisuluBot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tg_application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "app_id")
    private Long appId;

    @Column(name = "text", columnDefinition = "TEXT")
    private String text;

    @Column(name = "send_time")
    private LocalDateTime sendTime;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    private Group group;
}
