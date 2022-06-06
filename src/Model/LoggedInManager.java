package Model;

import java.util.ArrayList;
import java.util.List;

public class LoggedInManager {
    private final List<User> loggedInUsers;
    private final List<User> friends;

    //private final List<User> friends;
    private User thisUser;

    public LoggedInManager(User thisUser){
        loggedInUsers = new ArrayList<>();
        friends = new ArrayList<>();
        this.thisUser = thisUser;
    }

    public void add(User user){
        loggedInUsers.add(user);
    }

    public void addFriend(User user) {
        friends.add(user);
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

    public String[] getFriendsStringArray() {
        return friends.stream().map(user -> user.getUsername()).toList().toArray(new String[0]);
    }
    public User getByUserName(String username) {
        for(User user : loggedInUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public User getFriendsByUserName(String username) {
        for(User user : loggedInUsers){
            if(user.getUsername().equals(username)){
                return user;
            }
        }
        return null;
    }

    public List<User> getLoggedInUsers() {
        return loggedInUsers;
    }

    public List<User> getFriends() {
        return friends;
    }

    public User[] friendsOnline(User user, String[] usersFriend){
        User[] friendsOnline = new User[usersFriend.length];
        for(int i=0; i <= usersFriend.length; i++){
            //spara alla vänner i listan som är online till en ny lista
        }
        return friendsOnline;
    }
}
