package com.calebco.caleb.prototype1;

public class VideoCardClass {

    private String title;


    private String id;
    private String recipient;
    private String date;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VideoCardClass(String title, String recipient, String date, String id) {
        this.title = title;
        this.recipient = recipient;
        this.date = date;
        this.id = id;
    }
    public VideoCardClass(){

    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
