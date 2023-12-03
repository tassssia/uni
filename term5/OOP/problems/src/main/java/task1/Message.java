package task1;

import java.io.Serializable;

public class Message implements Serializable {

    private String sender;
    private String text;

    public Message(String sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return  getSender() + ": "  + getText();
    }
}