package ca.danieljameswilson.fakechat;

/**
 * Created by Daniel on 2016-12-01.
 */

public class Message {
    private String message;
    private String user;
    private int chatroom;

    public Message(String message, String user, int room){
        this.message = message;
        this.user = user;
        this.chatroom = room;
    }

    public String getMessage() {
        return message;
    }

    public String getUser() {
        return user;
    }

    public int getChatroom() {
        return chatroom;
    }
}
