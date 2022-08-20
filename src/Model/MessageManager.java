package Model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//TODO: Ta bort?
/*
public class MessageManager {
    private Buffer<ChatMessage> messageBuffer;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    private ChatMessage message;
    private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public MessageManager(Buffer<ChatMessage> messageBuffer){
        this.messageBuffer = messageBuffer;//sparar buffern skapad i controller
    }

    public void addPropertyChangeListener(PropertyChangeListener listener){
        this.pcs.addPropertyChangeListener(listener);
    }

    public void start(){
        new MessageThread(messageBuffer).start();
    }

    public void setMessage(ChatMessage message){
        this.message = message;
        this.pcs.firePropertyChange("message", null, message);
    }

    private class MessageThread extends Thread{
        private Buffer<ChatMessage> messageBuffer;

        public MessageThread(Buffer<ChatMessage> messageBuffer) {
            this.messageBuffer = messageBuffer;
        }

        @Override
        public void run(){
            while (!Thread.interrupted()){
                try {
                    message = this.messageBuffer.get();
                    setMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
} */
