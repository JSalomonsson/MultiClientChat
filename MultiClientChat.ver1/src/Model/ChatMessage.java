package Model;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.ArrayList;

//chatmeddelande
    public class ChatMessage implements Serializable {
        private ArrayList<String> receivers; //arraylist of usernames
        private String sender; //sending username
        private ImageIcon Userimage; //sending user's image
        private String messageText;
        private Image image;

        /*
        en ArrayList talar om att detta är ett objekt med strängar man loopa över, i princip alla listor i java implementerar ArrayList så
        typ av lista är ej fördefinierat utan den tar emot whatever (typ)
         */
        public ChatMessage(ArrayList<String> receivers, String msg) { //för meddelande med enbart text
            this.receivers = new ArrayList<String>(receivers); //en arraylist av alla som finns i ArrayList receivers-argumentet
            this.messageText = msg;
            System.out.println("I MESSAGE KLASSEN: " + msg);

        }


        public ChatMessage(ArrayList<String> receivers, String msg, Image image){//för meddelande med text + bild
            this.receivers = new ArrayList<>(receivers);
            this.messageText = msg;
            this.image = image;
        }

        public ChatMessage(ArrayList<String> receivers, Image image){//för meddelande med bara bild
            this.receivers = new ArrayList<>(receivers);
            this.image = image;
        }

    public String getMessageText() {
        return messageText;
    }

    public ArrayList<String> getReceivers() {
        return receivers;
    }
}

