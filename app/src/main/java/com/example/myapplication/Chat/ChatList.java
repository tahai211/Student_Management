package com.example.myapplication.Chat;

public class ChatList {
    private String user,name,message,date,time;

    public ChatList(String user, String name, String message, String date, String time) {
        this.user = user;
        this.name = name;
        this.message = message;
        this.date = date;
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
}
