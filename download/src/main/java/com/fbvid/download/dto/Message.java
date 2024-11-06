package com.fbvid.download.dto;

public class Message {

    private long message_id;
    private From from;
    private Chat chat;
    private long date;
    private String text;

        // Getters and Setters

    public long getMessage_id() {
        return message_id;
    }

    public void setMessageId(long messageId) {
        this.message_id = messageId;
    }

    public From getFrom() {
        return from;
    }

    public void setFrom(From from) {
        this.from = from;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
