package bot.AisuluBot.bot.utils;

import lombok.experimental.UtilityClass;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class Buttons {

    public static ReplyKeyboard threeButtons(String text, String text2, String text3) {
        return buttons(new String[]{text, text2, text3});
    }

    public static ReplyKeyboard twoButton(String text, String text2) {
        return buttons(new String[]{text, text2});
    }

    public static ReplyKeyboard oneButton(String text) {
        return buttons(new String[]{text});
    }


    public static ReplyKeyboard buttons(List<String> buttonTexts, String... buttonText) {
        buttonTexts.addAll(Arrays.asList(buttonText));
        return buttons(buttonTexts.toArray(new String[0]));
    }

    public static ReplyKeyboard buttons(List<String> buttonTexts) {
        return buttons(buttonTexts.toArray(new String[0]));
    }

    public static ReplyKeyboard buttons(String[] buttonTexts) {
        ReplyKeyboardMarkup replyKeyboardMarkup = (ReplyKeyboardMarkup) replyKeyboard();
        List<KeyboardRow> listRows = new ArrayList<>();

        for (String b : buttonTexts) {
            KeyboardRow keyboardRow = new KeyboardRow();
            keyboardRow.add(new KeyboardButton(b));
            listRows.add(keyboardRow);
        }

        replyKeyboardMarkup.setKeyboard(listRows);

        return replyKeyboardMarkup;
    }

    private static ReplyKeyboard replyKeyboard() {
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setSelective(true);
        return replyKeyboardMarkup;
    }
}
