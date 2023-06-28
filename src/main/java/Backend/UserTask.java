package Backend;

public class UserTask {

    private String pair;
    private String direction;
    private boolean taskState;
    private int targetPrice;
    private String userID;

    private String chatID;
    private String taskID;


    public String getChatID()
    {
        return chatID;
    }

    public void setChatID(String chatID)
    {
        this.chatID = chatID;
    }

    public String getTaskID()
    {
        return taskID;
    }

    public void setTaskID(String taskID)
    {
        this.taskID = taskID;
    }


    // pair
    public String getPair() {
        return pair;
    }

    public void setPair(String pair)
    {
        this.pair = pair;
    }

    // userid
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID()
    {
        return this.userID;
    }

    //direction
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    // taskState

    public boolean getState()
    {
        return taskState;
    }

    public void setState(boolean taskState)
    {
        this.taskState = taskState;
    }

    // targetPrice

    public void setTargetPrice(int targetPrice)
    {
        this.targetPrice = targetPrice;
    }

    public int getTargetPrice()
    {
        return targetPrice;
    }














}
