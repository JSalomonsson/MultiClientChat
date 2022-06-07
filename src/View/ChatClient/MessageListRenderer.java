package View.ChatClient;

import Model.ChatMessage;

import javax.swing.*;
import java.awt.*;

/**
 * Custom renderer for Jlist to be able to display pictures
 */
public class MessageListRenderer extends DefaultListCellRenderer {

    public MessageListRenderer() { super(); }

    /**
     * Goes throgh every value in list and sets the text and adds the image from the chat message.
     */
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        ChatMessage message = (ChatMessage) value;
        label.setText(message.getSender().getUsername() + ": " + message.getMessageText());
        label.setToolTipText(message.getReceivedByServerAt());
        ImageIcon icon = message.getImage();
        if(icon != null){
            label.setIcon(icon);
        }
        label.setHorizontalTextPosition(SwingConstants.LEFT);
        return label;
    }
}
