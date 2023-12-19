package data;

import java.io.Serializable;
import java.util.ArrayList;

public class Email implements Serializable {
    private int id;
    private String topic;
    //other fields

    public Email(int id, String topic) {
        this.id = id;
        this.topic = topic;
        // other fields
    }

    @Override
    public String toString() {
        return " >> " + id + " - " + topic + " - ...";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
