package Telegram;


import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.groupadministration.RestrictChatMember;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Web extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        // It's okay that here is nothing .
    }
    @Override
    public String getBotUsername() {
        return BotSettings.BOT_NAME;
    }
    @Override
    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }


    // Send message method, that used in all Handlers except DebugHandler.
    public void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}