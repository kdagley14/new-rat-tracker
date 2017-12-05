package team10x.cs2340.rattracker2;

/**
 * Created by kdagley on 12/4/17.
 */

public class User {
    private String username;
    private int isLocked;

    public User (String user, int locked) {
        username = user;
        isLocked = locked;
    }

    public int getLockedStatus() {
        return isLocked;
    }

    public void setLockedStatus(int num) {
        isLocked = num;
    }

    public String getUsername() {
        return username;
    }

    public String toString() {
        return "" + username + ", locked: " + isLocked;
    }

}
