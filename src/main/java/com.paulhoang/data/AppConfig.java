package com.paulhoang.data;

/**
 * Created by paul on 29/12/2016.
 */
public class AppConfig {
    private String messageBusLocation;
    private String topic;

    public String getMessageBusLocation() {
        return messageBusLocation;
    }

    public void setMessageBusLocation(String messageBusLocation) {
        this.messageBusLocation = messageBusLocation;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
