package bot.AisuluBot.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DateFormat {

    DD_MM_YYYY_HH_MM("dd.MM.yyyy HH:mm");


    private final String value;
}
