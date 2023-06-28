package Telegram;

import Backend.ChatHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.*;


public class Bot extends TelegramLongPollingBot {


    //  Handlers Initialize;

    ChatHandler MainHandler = new ChatHandler();

    @Override
    public void onUpdateReceived(Update update) {


        String desktopPath = System.getProperty("user.home") + "/Desktop/";
        String fileName = "log.txt";
        String logOutput = "username: " + update.getMessage().getFrom().getUserName() + " chatid: " + update.getMessage().getChatId() + " message: " + update.getMessage().getText() + "\n";

        try {
            FileWriter writer = new FileWriter(desktopPath + fileName, true);
            writer.write(logOutput);
            writer.close();
        } catch (Exception OnWriteLog) {
            OnWriteLog.printStackTrace();
        }


        try{
            MainHandler.Handle(update);
        } catch (Exception e) {
            System.out.println("Something Went Wrong at ChatHandler!");

            Web Sender = new Web();
            String stackTrace = "";
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            stackTrace = sw.toString();

            e.printStackTrace();

            String error = "error detected!, user: " + update.getMessage().getFrom() + "\n\nStacktrace!:\n\n" + stackTrace;
           // Sender.sendMessage("428118522", error);
        }
    }

    // DEFAULT
    @Override
    public String getBotUsername() {
        return BotSettings.BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BotSettings.BOT_TOKEN;
    }
}


