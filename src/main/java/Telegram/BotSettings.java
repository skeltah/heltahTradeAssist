package Telegram;

import Backend.UserObject;
import Backend.UserTask;

import java.util.ArrayList;

public class BotSettings {

    //telegram side
    public static final String BOT_TOKEN = "6192838527:AAGJsF4XUfeMbcuv2GkncV9ejScqOMxBY4M";
    public static final String BOT_NAME = "@heltahalert_bot";
    public static final String API_KEY = "vv4v82jMNXgB9SsEgx8SV17PN1f56pCsbbd8XFwhhChRke5Cp2nBs49MRb5yVQzN";
    public static final String SECRET_KEY = "aYNn82JSFFcb6LyvA8fwbXqmZAj2wqeJwZ3cWQOWLpDMQHIJwKwgZY12RZ5bjym1";

    //

    public static UserObject currentUser;
    public static ArrayList<UserObject> users = new ArrayList<>();
    public static ArrayList<UserTask> tasks = new ArrayList<>();
}
