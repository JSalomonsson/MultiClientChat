package Model;

import javax.swing.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The class for the messages sent in the chat.
 */
    public class ChatMessage implements Serializable {
        private ArrayList<User> receivers; //arraylist of usernames
        private User sender; //sending username
        private String messageText;
        private ImageIcon image;
        private LocalDateTime receivedByServerAt;
        private LocalDateTime deliveredToReceiverAt;

        public ChatMessage(ArrayList<User> receivers, String msg, ImageIcon image, User sender){//för meddelande med text + bild
            this.receivers = new ArrayList<User>(receivers);
            this.messageText = msg;
            this.image = image;
            this.sender = sender;
        }

    public User getSender() {
        return sender;
    }

    public String getMessageText() {
        return messageText;
    }

    public ArrayList<User> getReceivers() {
        return receivers;
    }

    public ImageIcon getImage() {
        return image;
    }

    public String getReceivedByServerAt() {
        return receivedByServerAt.format(DateTimeFormatter.ofPattern(("yyyy.MM.dd HH:mm")));
    }

    public void setReceivedByServerAt(LocalDateTime receivedByServerAt) {
        this.receivedByServerAt = receivedByServerAt;
    }

    public String getDeliveredToReceiverAt() {
        return deliveredToReceiverAt.format(DateTimeFormatter.ofPattern(("yyyy.MM.dd HH:mm")));
    }

    public void setDeliveredToReceiverAt(LocalDateTime deliveredToReceiverAt) {
        this.deliveredToReceiverAt = deliveredToReceiverAt;
    }
}

