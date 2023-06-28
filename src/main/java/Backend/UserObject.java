package Backend;

import java.util.ArrayList;

public class UserObject {
    private String userId;
    private String chatId;
    private String apiKey = "not_set";
    private String secretKey = "not_set";
    private boolean everydayReport = false;
    private String reportTime = "12:00";
    private ArrayList<String> trackingPairs = new ArrayList<>();


    // set time

    public void setEverydayReportTime(String Time)
    {
        this.reportTime = Time;
    }

    public String getEverydayReportTime()
    {
        return this.reportTime;
    }

    // everyday report boolean

    public void setEverydayReport(boolean necessity)
    {
        this.everydayReport = necessity;
    }

    public boolean getEverydayReport()
    {
        return this.everydayReport;
    }

    // pair tracking
    public void addPairToTracking(String Pair)
    {
        Pair = Pair.toUpperCase();

        this.trackingPairs.add(Pair);
    }

    public void removePairFromTracking(String Pair)
    {
        Pair = Pair.toUpperCase();

        this.trackingPairs.remove(Pair);
    }

    public ArrayList<String> getTrackingPairsList()
    {
        return this.trackingPairs;
    }

    // Геттеры и сеттеры для поля userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    // Геттеры и сеттеры для поля chatId
    public String getChatId() {
        return chatId;
    }
    public void setChatId(String chatId) {
        this.chatId = chatId;
    }


    // Геттеры и сеттеры для поля apiKey
    public String getApiKey() {
        return apiKey;
    }
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    // Геттеры и сеттеры для поля secretKey
    public String getSecretKey() {
        return secretKey;
    }
    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
