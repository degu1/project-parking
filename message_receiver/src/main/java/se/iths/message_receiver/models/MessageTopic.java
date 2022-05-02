package se.iths.message_receiver.models;

public enum MessageTopic {
    IN_PARKING("IN_PARKING"),
    IN_QUEUE("IN_QUEUE"),
    QUEUE_UPDATED("QUEUE_UPDATED");

    private final String name;

    MessageTopic(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
