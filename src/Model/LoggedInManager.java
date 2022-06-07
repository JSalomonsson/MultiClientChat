package Model;

import Controller.ClientController;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class LoggedInManager {
    private final List<User> loggedInUsers;
    private final List<User> friends;

    private ClientController controller;

    public LoggedInManager(ClientController controller){
        loggedInUsers = new ArrayList<>();
        friends = new ArrayList<>();
        this.controller = controller;
    }

    public void add(User user){
        loggedInUsers.add(user);
    }

    public boolean addFriend(User user) {
        for(User friend : friends){
            if(user.getUsername().equals(friend.getUsername())){
                return false;
            }
        }
        friends.add(user);
        saveFriends();
        return true;
    }

    private void saveFriends() {
        try(FileOutputStream fos = new FileOutputStream("files/" + controller.getUser().getUsername() + ".dat");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos)){
            oos.writeInt(friends.size());
            for(User friend : friends){
                oos.writeObject(friend);
            }
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFriends(){
        boolean fileExists = new File("files/" + controller.getUser().getUsername() + ".dat").isFile();
        if(fileExists){
            try(FileInputStream fis = new FileInputStream("files/" + controller.getUser().getUsername() + ".dat");
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis)){
                int size = ois.readInt();
                for(int i = 0; i < size; i++){
                    Object temp = ois.readObject();
                    if(temp instanceof User friend){
                        friends.add(friend);
                    }
                }
            }catch(IOException | ClassNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for(User user : loggedInUsers){
            if(!user.getUsername().equals(controller.getUser().getUsername())) {
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
        for(User user : friends){
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
