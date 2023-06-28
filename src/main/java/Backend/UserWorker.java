package Backend;

import Telegram.BotSettings;

public class UserWorker {

    public UserObject getCurrentUser(String userId)
    {
        UserObject userToReturn = null;

        for(UserObject current : BotSettings.users)
        {
            if(current.getUserId().equals(userId))
            {
                userToReturn = current;
            }
        }

        return userToReturn;
    }

    public boolean isThatUserExists(String userId)
    {
        boolean finded = false;

        for(UserObject current : BotSettings.users)
        {
            if (current.getUserId().equals(userId)) {
                finded = true;
                break;
            }
        }

        return finded;
    }

}
