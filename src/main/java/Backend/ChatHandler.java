package Backend;

import Telegram.Bot;
import Telegram.BotSettings;
import Telegram.Web;
import com.binance.api.client.domain.account.AssetBalance;
import com.binance.api.client.domain.general.Asset;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class ChatHandler {

    public void Handle(Update update) throws Exception {

        Web Sender = new Web();
        UserWorker Worker = new UserWorker();
        Message ReceivedMessage = update.getMessage();
        String ChatID = ReceivedMessage.getChatId().toString();
        String UserID = ReceivedMessage.getFrom().getId().toString();
        String UserName = ReceivedMessage.getFrom().getUserName();
        String msgContent = ReceivedMessage.getText();
        String message = "Request incorrect! ⛔";
        msgContent = msgContent.replaceAll("_"," ");

        if (Worker.isThatUserExists(UserID)) {

            BotSettings.currentUser = Worker.getCurrentUser(UserID);

            if(msgContent.contains("/settings"))
            {

                String trackingPairs = "";
                String trackMessage = "";

                if(BotSettings.currentUser.getTrackingPairsList().size()>0) {

                    trackingPairs = "\n\n Tracking: ";

                    for (String currentPair : BotSettings.currentUser.getTrackingPairsList()) {
                        trackingPairs = trackingPairs + currentPair;
                        if (BotSettings.currentUser.getTrackingPairsList().size() > 1) {
                            trackingPairs = trackingPairs + ", ";
                        }
                    }
                }

                if(BotSettings.currentUser.getEverydayReport())
                {
                    trackMessage = "on, turn off: /report_off";
                }
                else
                {
                    trackMessage = "off, turn on: /report_on";
                }





                message = UserName + ", your settings ⚙\uFE0F"
                        + trackingPairs + "\n"
                        + "\n Report time: " + BotSettings.currentUser.getEverydayReportTime()
                        + "\n Timed report " + trackMessage + "\n"
                        + "\n\uD83D\uDD11 API KEY: " + BotSettings.currentUser.getApiKey()
                        + "\n\n\uD83E\uDD77 SECRET: " + BotSettings.currentUser.getSecretKey()
                        + "\n\nif they equals 'not_set', you need"
                        + "\nset up them by specific commands!";
            }

            // API SECTION

            if(msgContent.contains("/setapi "))
            {
                String[] temp = msgContent.split(" ");
                BotSettings.currentUser.setApiKey(temp[1]);
                message = "API key \uD83D\uDD11 set up.";
            }
            else if(msgContent.contains("/setkey "))
            {
                String[] temp = msgContent.split(" ");
                BotSettings.currentUser.setSecretKey(temp[1]);
                message = "SECRET key \uD83D\uDD10 set up.";
            }
            else if(msgContent.contains("/setpair "))
            {
                String[] temp = msgContent.split(" ");
                BotSettings.currentUser.setApiKey(temp[1]);
                BotSettings.currentUser.setSecretKey(temp[2]);
                message = "Key pair \uD83D\uDD10 set up";
            }

            // HELP ANSWERS

            if(msgContent.equalsIgnoreCase("/setapi"))
            {
                message = "using: /setapi KEY";
            }
            else if(msgContent.equalsIgnoreCase("/setkey"))
            {
                message = "using: /setkey SECRETKEY";
            }
            else if(msgContent.equalsIgnoreCase("/setpair"))
            {
                message = "using: /setpair KEY SECRETKEY";
            }
            if(msgContent.equalsIgnoreCase("/support"))
            {
                BinanceHandler Binance = new BinanceHandler();

                message = "Developer contact: @skeltahh"
                        + "\n\nData that can be useful:"
                        + "\n\n UserID: " + UserID
                        + "\n ChatID: " + ChatID
                        + "\n ObjectID: " + Worker.getCurrentUser(UserID)
                        + "\n API STATUS: " + Binance.CheckConnection(BotSettings.currentUser.getApiKey(),BotSettings.currentUser.getSecretKey())
                        + "\n ReportED: " + BotSettings.currentUser.getEverydayReport()
                        + "\n RepTime: " + BotSettings.currentUser.getEverydayReportTime();
            }

            // CHECKER

            if(msgContent.contains("/check"))
            {
                UserObject thisUser = Worker.getCurrentUser(UserID);
                BinanceHandler Binance = new BinanceHandler();

                if(Binance.CheckConnection(thisUser.getApiKey(),thisUser.getSecretKey()))
                {
                    message = "API/SECRET ✅ correct, binance server responded.";
                }
                else
                {
                    message = "API/SECRET ❌ is incorrect or not set up. ";
                }

            }

            // TASK BLOCK

            if(msgContent.contains("/newtask "))
            {
                // PAIR / PRICE / DIRECTION

                String pair;
                String direction;
                Random rand = new Random();

                String[] temp = msgContent.split(" ");

                UserTask newTask = new UserTask();

                if(temp[1].equalsIgnoreCase("бтк"))
                {
                    pair = "BTCUSDT";
                }
                else
                {
                    pair = temp[1];
                }

                if(temp[3].equalsIgnoreCase("в"))
                {
                    direction = "up";
                }
                else
                {
                    direction = "down";
                }

                int targetPrice = Integer.parseInt(temp[2]);


                pair = pair.toUpperCase();

                newTask.setPair(pair);
                newTask.setDirection(direction);
                newTask.setTargetPrice(targetPrice);
                newTask.setUserID(UserID);
                newTask.setChatID(ChatID);
                newTask.setTaskID(String.valueOf(rand.nextInt(100000)));

                try {
                    BotSettings.tasks.add(newTask);
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                message = "New task added to your list \uD83D\uDCDD \n\uD83E\uDD16 started watching, \n\n use /watchlist to manage tasks!";

            }
            else if(msgContent.equalsIgnoreCase("/watchlist"))
            {
                message = UserName + ", your tasks: \n\n";

                ArrayList<UserTask> currentUserTasks = new ArrayList<>();

                for(UserTask currentTask : BotSettings.tasks)
                {
                    if(currentTask.getUserID().equals(UserID))
                    {
                        currentUserTasks.add(currentTask);
                    }
                }

                int taskIterator = 1;

                for(UserTask task : currentUserTasks)
                {
                    message = message + taskIterator + ".\n\n Target price: " + task.getTargetPrice() + "$"
                            + "\n Coin Pair: " + task.getPair() + "\n";
                    if(task.getDirection().equals("up"))
                    {
                        message = message + " Direction: long \uD83D\uDCC8 \n\n";
                    }
                    else
                    {
                        message = message + " Direction: short \uD83D\uDCC9 \n\n";
                    }

                    message = message + "remove - /deltask_" + task.getTaskID() + "\n\n";



                    taskIterator++;
                }

                if(currentUserTasks.size() < 1)
                {
                    message = UserName + ", your watchlist is empty right now!";
                }

            }
            else if(msgContent.contains("/deltask "))
            {
                String[] temp = msgContent.split(" ");

                for(int i = 0; i < BotSettings.tasks.size(); i++)
                {
                    UserTask current = BotSettings.tasks.get(i);
                    if(current.getTaskID().equals(temp[1]))
                    {
                        BotSettings.tasks.remove(i);
                        message = "Task #" + temp[1] + " removed from your list! \n\n \uD83E\uDEF3\uD83D\uDCC4 \uD83D\uDDD1\uFE0F";
                    }
                }
            }

            //everyday report

            if(msgContent.contains("/track "))
            {
                String[] temp = msgContent.split(" ");
                String pair = temp[1].toUpperCase();
                BinanceHandler Binance = new BinanceHandler();

                if(Binance.isThatPairExists(pair)) {

                    if (!BotSettings.currentUser.getTrackingPairsList().contains(pair)) {
                        BotSettings.currentUser.addPairToTracking(pair);
                        message = "Pair " + pair + " added to tracking ✏\uFE0F\uD83D\uDCCB";
                    } else {
                        message = "Pair " + pair + " already in your track list ⛔";
                    }
                }
                else
                {
                    message = "Pair " + pair + " doesnt exists ⛔";
                }
            }
            else if(msgContent.contains("/untrack "))
            {
                String[] temp = msgContent.split(" ");
                String pair = temp[1].toUpperCase();

                if(BotSettings.currentUser.getTrackingPairsList().contains(pair))
                {
                    BotSettings.currentUser.removePairFromTracking(pair);
                    message = "Pair " + pair + " successfully removed from tracking \uD83E\uDEF3\uD83D\uDCC4 \uD83D\uDDD1\uFE0F";
                }
                else
                {
                    message = "Pair " + pair + " doesn't exist in your track list. \nmaybe incorrect input?";
                }
            }
            else if(msgContent.equalsIgnoreCase("/report"))
            {
                if(BotSettings.currentUser.getTrackingPairsList().size()>0)
                {
                    BinanceHandler Binance = new BinanceHandler();

                    message = UserName + ", your track report \uD83D\uDCCB\n\n";


                    for(String currentPair : BotSettings.currentUser.getTrackingPairsList())
                    {
                        message = message + Binance.getReport(currentPair) + "\n\n";
                    }

                }
                else
                {
                    message = "Your tracking list is empty.";
                }


            }
            else if(msgContent.contains("/timeset "))
            {
                String[] temp = msgContent.split(" ");
                String time = temp[1];

                BotSettings.currentUser.setEverydayReportTime(time);

                message = UserName+", report time set to " + time;
            }

            // EASIER LIFE BLOCK

            if(msgContent.contains("/price "))
            {
                String[] temp = msgContent.split(" ");
                BinanceHandler Binance = new BinanceHandler();
                message = "Price of " + temp[1] + " is " + Binance.getPrice(temp[1]) + " $";
            }
            else if(msgContent.contains("/report "))
            {

                String[] temp = msgContent.split(" ");
                String pair = temp[1].toUpperCase();

                if(pair.equalsIgnoreCase("off") || pair.equalsIgnoreCase("on"))
                {
                    if(pair.equalsIgnoreCase("on"))
                    {
                        BotSettings.currentUser.setEverydayReport(true);
                        message = "Everyday report activated";
                    }
                    else
                    {
                        BotSettings.currentUser.setEverydayReport(false);
                        message = "Everyday report deactivated";
                    }
                }
                else {
                    BinanceHandler Binance = new BinanceHandler();
                    message = Binance.getReport(pair);
                }
            }
            if(msgContent.equals("/wallet"))
            {
                BinanceHandler Binance = new BinanceHandler();

                UserObject thisUser = Worker.getCurrentUser(UserID);

                if(!Binance.CheckConnection(thisUser.getApiKey(),thisUser.getSecretKey()))
                {
                    message = UserName + ", your API/SECRET not set up properly. \nPlease set up them and check by /check";
                }
                else
                {
                    message = "Wallet: \n";

                    ArrayList<AssetBalance> notZeroBalances = new ArrayList<>();
                    ArrayList<AssetBalance> notZeroLocked = new ArrayList<>();

                   for(AssetBalance currentAsset : Binance.client.getAccount().getBalances())
                   {
                       if(Double.parseDouble(currentAsset.getFree()) > 0)
                       {
                           notZeroBalances.add(currentAsset);
                       }

                       if(Double.parseDouble(currentAsset.getLocked()) >0)
                       {
                           notZeroLocked.add(currentAsset);
                       }

                   }

                   if(notZeroBalances.size()>0)
                   {
                       for(AssetBalance currentAsset : notZeroBalances)
                       {
                           message = message + "\n " + currentAsset.getAsset() + ": " + currentAsset.getFree();
                       }
                   }

                   message = message + "\n\n";

                   if(notZeroLocked.size()>0)
                   {
                       for(AssetBalance currentAsset : notZeroLocked)
                       {
                           message = message + currentAsset.getAsset() + ": " + currentAsset.getLocked();
                       }
                   }

                }
            }

            try {
                if (!message.isBlank()) {
                    Sender.sendMessage(ChatID, message);
                }
            } catch (Exception e) {
                throw new Exception();
            }
        }
        else {
            UserObject newUser = new UserObject();
            newUser.setChatId(ChatID);
            newUser.setUserId(UserID);
            BotSettings.users.add(newUser);
            String toSend = "Hello, " + ReceivedMessage.getFrom().getFirstName() + "\nplease use /settings";
            Sender.sendMessage(ChatID,toSend);
        }
    }
}
