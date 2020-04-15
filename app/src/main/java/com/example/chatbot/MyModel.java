package com.example.chatbot;

public class MyModel {

    private String message;
    //tells whether its bot or me(user)
    boolean isMe;

    public MyModel(String message, Boolean isMe) {
        this.message = message;
        this.isMe = isMe;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isMe() {
        return isMe;
    }

    public void setMe(boolean me) {
        isMe = me;
    }
}
