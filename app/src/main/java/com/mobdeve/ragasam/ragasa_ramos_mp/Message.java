package com.mobdeve.ragasam.ragasa_ramos_mp;

public class Message{
    String id;
    String content;
    boolean shareLocation;

    public Message(String id, String content, boolean shareLocation) {
        this.id = id;
        this.content = content;
        this.shareLocation = shareLocation;
    }

    public void updateContent(String content){this.content = content;}

    public void updateShareLocation(boolean shareLocation){this.shareLocation = shareLocation;}

    public String getMessageID(){return this.id;}

    public String getMessageContent(){return this.content;}

    public boolean getShareLocation(){return this.shareLocation;}
}