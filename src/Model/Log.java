package Model;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Class for the log that keeps track of what happens.
 */
public class Log implements Serializable {
    @Serial
    private static final long serialVersionUID = 1;
    LocalDateTime time;
    String text;

    /**
     * Constructor that contains a String and a time.
     */
    public Log(String text) {
        this.text = text;
        this.time = LocalDateTime.now();
    }

    public String getTimeAsString() {
        return time.format(DateTimeFormatter.ofPattern(("yyyy.MM.dd HH:mm")));
    }

    public LocalDateTime getTime() {
        return time;
    }

    public String getText() {
        return text;
    }
}
