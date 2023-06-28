import Backend.BinanceHandler;
import Backend.UserObject;
import Backend.UserTask;
import Telegram.Bot;
import Telegram.BotSettings;
import Telegram.Web;
import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Timer;
import java.util.TimerTask;

public class Execute {

    public static void main(String[] args) {


        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

        Timer timer = new Timer();
        Web Sender = new Web();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                for (UserTask currentTask : BotSettings.tasks) {
                    UserObject currentTaskUser = BotSettings.currentUser;

                    for (UserObject user : BotSettings.users) {
                        if (currentTask.getUserID().equals(user.getUserId())) {
                            currentTaskUser = user;
                        }
                    }

                    BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(currentTaskUser.getApiKey(), currentTaskUser.getSecretKey());
                    BinanceApiRestClient client = factory.newRestClient();

                    int realtimePrice = (int) Double.parseDouble(client.getPrice(currentTask.getPair()).getPrice());

                    if (currentTask.getDirection().equals("up")) {

                        if (realtimePrice > currentTask.getTargetPrice()) {
                            currentTask.setState(true);

                            //  Sender.sendMessage(currentTaskUser.getChatId(), "Task " + currentTask.getTaskID() + " confirmed!");

                        }

                    } else {

                        if (realtimePrice < currentTask.getTargetPrice()) {
                            currentTask.setState(true);

                            //  Sender.sendMessage(currentTaskUser.getChatId(), "Task " + currentTask.getTaskID() + " confirmed!");

                        }
                    }
                }
            }
        }, 0, 1000 * 30); // time

        timer.schedule(new TimerTask() {
            @Override
            public void run() {

                for (UserTask currentTask : BotSettings.tasks) {
                    if (currentTask.getState()) {

                        if (currentTask.getDirection().equals("up")) {

                            String message = "          ❗ Price Alert ❗ " + "\n\n "
                                    + "  Coin pair " + currentTask.getPair() + " higher than " + currentTask.getTargetPrice() + "$"
                                    + "\n\n stop notifications: /deltask_" + currentTask.getTaskID();
                            Sender.sendMessage(currentTask.getChatID(), message);

                        } else {


                            String message = "          ❗ Price Alert ❗ " + "\n\n "
                                    + "  Coin pair " + currentTask.getPair() + " lower than " + currentTask.getTargetPrice() + "$"
                                    + "\n\n stop notifications: /deltask_" + currentTask.getTaskID();
                            Sender.sendMessage(currentTask.getChatID(), message);


                        }
                    }
                }
            }
        }, 0, 1000 * 5); // time

        timer.schedule(new TimerTask() {
            @Override
            public void run() {


                for (UserObject currentUser : BotSettings.users) {
                    if (currentUser.getEverydayReport()) {
                        String message;

                        LocalTime currentTime = LocalTime.now();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                        String formattedTime = currentTime.format(formatter);

                        System.out.println(formattedTime);
                        System.out.println(currentUser.getEverydayReportTime());

                        if (formattedTime.equals(currentUser.getEverydayReportTime())) {

                            if (BotSettings.currentUser.getTrackingPairsList().size() > 0) {
                                BinanceHandler Binance = new BinanceHandler();

                                message = "Dear user, your daily autoreport \uD83D\uDCCB\n\n";


                                for (String currentPair : BotSettings.currentUser.getTrackingPairsList()) {
                                    message = message + Binance.getReport(currentPair) + "\n\n";
                                }

                            } else {
                                message = "You got autoreport: on, \nbut your tracklist is empty!";
                            }

                            Web Sender = new Web();
                            Sender.sendMessage(currentUser.getChatId(), message);
                        }
                    }
                }


            }
        }, 0, 1000 * 60);
    }
}