package Model;
import javax.swing.*;
import java.awt.*;
import java.io.Serializable;

public class User implements Serializable {

    private String username;
    private ImageIcon icon;

    public User(String username, ImageIcon icon) {
        this.username = username;
        this.icon = icon;

        System.out.println("New user created, name: " + username);

    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public int hashCode() {
        return username.hashCode();
    }

    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof User)
            return username.equals(((User)obj).getUsername());
        return false;
    }
}






