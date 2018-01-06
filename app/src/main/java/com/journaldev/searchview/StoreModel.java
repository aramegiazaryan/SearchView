package com.journaldev.searchview;

public class StoreModel {

 private long id;
 private String title;
 private String type;
 private String tag;
 private String linkIcon;
 private String linkSource;
 private String linkSound;
 private String price;
 private String date;
 private int stateDownload;


    public StoreModel(long id, String title, String type, String tag, String linkIcon, String linkSource, String linkSound, String price, String date, int stateDownload) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.tag = tag;
        this.linkIcon = linkIcon;
        this.linkSource = linkSource;
        this.linkSound = linkSound;
        this.price = price;
        this.date = date;
        this.stateDownload = stateDownload;
    }

    public StoreModel(String title, String type, String linkIcon, String price, int stateDownload) {
        this.title = title;
        this.type = type;
        this.linkIcon = linkIcon;
        this.price = price;
        this.stateDownload = stateDownload;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getTag() {
        return tag;
    }

    public String getLinkIcon() {
        return linkIcon;
    }

    public String getLinkSource() {
        return linkSource;
    }

    public String getLinkSound() {
        return linkSound;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public int getStateDownload() {
        return stateDownload;
    }
}
