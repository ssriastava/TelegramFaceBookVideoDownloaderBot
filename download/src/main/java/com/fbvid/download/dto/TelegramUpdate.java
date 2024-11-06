package com.fbvid.download.dto;

public class TelegramUpdate {

    private long update_id;

    private Message message;

    // Getters and Setters

    public long getUpdateId() {
        return update_id;
    }

    public void setUpdateId(long update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
