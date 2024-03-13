package lab.andersen.util;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.request.SendDocument;
import com.pengrad.telegrambot.request.SendMessage;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;

public class TelegramSender {
    private static final String BOT_TOKEN = "tg.bot.token";
    private static final String CHAT_ID = "tg.bot.chat.id";
    private static final TelegramBot BOT;

    static {
        BOT = new TelegramBot(PropertiesUtils.get(BOT_TOKEN));

//        BOT.setUpdatesListener(updates -> {
//            updates.forEach((update) -> {
//                if (update.message() != null) {
//                    Long id = update.message().chat().id();
//                    BOT.execute(new SendMessage(id, id + ""));
//                }
//            });
//            return UpdatesListener.CONFIRMED_UPDATES_ALL;
//        }, e -> {
//            if (e.response() != null) {
//                e.response().errorCode();
//                e.response().description();
//            } else {
//                e.printStackTrace();
//            }
//        });
    }

    public static void sendMessage(String message) {
        BOT.execute(new SendMessage(PropertiesUtils.get(CHAT_ID), message));
    }

    public static void sendPDF(File pdf) {
        BOT.execute(new SendDocument(PropertiesUtils.get(CHAT_ID), pdf));
    }

    public static void main(String[] args) {
        URL res = TelegramSender.class.getClassLoader().getResource("dummy.pdf");
        File file = null;
        try {
            file = Paths.get(res.toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        sendPDF(file);
    }
}
