package Telegram;

import Backend.UserObject;
import Backend.UserTask;

import java.util.ArrayList;

public class BotSettings {

    //telegram side
    public static final String BOT_TOKEN = "";
    public static final String BOT_NAME = "@heltahalert_bot";
    public static final String API_KEY = "";
    public static final String SECRET_KEY = "";

    //

    public static UserObject currentUser;
    public static ArrayList<UserObject> users = new ArrayList<>();
    public static ArrayList<UserTask> tasks = new ArrayList<>();
}
