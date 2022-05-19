package Model;

import java.util.ArrayList;
import java.util.List;

public class LoggedInManager {
    private final List<User> loggedInUsers;

    //private final List<User> friends;
    private User thisUser;

    public LoggedInManager(User thisUser){
        loggedInUsers = new ArrayList<>();
        this.thisUser = thisUser;
    }

    public void add(User user){
        loggedInUsers.add(user);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(User user : loggedInUsers){
            if(!user.getUsername().equals(thisUser.getUsername())) {
                s.append(user.getUsername()).append("\n");
            }
        }

        return s.toString();
    }

    public void clear() {
        loggedInUsers.clear();
    }

    public String[] getAsStringArray() {
        return loggedInUsers.stream().map(user -> user.getUsername()).toList().toArray(new String[0]);
    }

    public User getByUserName(String username) {
        for(User user : loggedInUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }
}
