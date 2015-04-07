package net.samagames.tools;

public class JsonPrivateMessage {

    private UnknownPlayer sender;
    private UnknownPlayer receiver;
    private String message;

    public JsonPrivateMessage(UnknownPlayer sender, UnknownPlayer receiver, String message) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
    }

    public JsonPrivateMessage() {
    }

    public UnknownPlayer getSender() {
        return sender;
    }

    public UnknownPlayer getReceiver() {
        return receiver;
    }

    public String getMessage() {
        return message;
    }
}
