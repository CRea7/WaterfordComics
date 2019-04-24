package com.example.waterfordcomicsapp.models;

import java.io.Serializable;

public class Comic implements Serializable
{
    public String comicId;
    public String comicTitle;
    public String image;
    public String extension;
    public String issueNumber;
    public String storeDate;
    public String price;
    public String note;
    public String userEmail;


    public Comic() {}

    public Comic(String comicId, String Title, String image,String extension, String issueNumber, String storeDate, String price, String note, String userEmail)
    {
        this.comicId = comicId;
        this.comicTitle = Title;
        this.image = image;
        this.extension = extension;
        this.issueNumber = issueNumber;
        this.storeDate = storeDate;
        this.price = price;
        this.note = note;
        this.userEmail = userEmail;
    }

    @Override
    public String toString() {
        return comicId + ", " + comicTitle + ", " + image + ", " + extension + ", " + issueNumber
                + ", " + storeDate + ", " + price + ", " + note + ", " + userEmail;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getComicTitle() {
        return comicTitle;
    }

    public void setComicTitle(String comicTitle) {
        this.comicTitle = comicTitle;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    public String getStoreDate() {
        return storeDate;
    }

    public void setStoreDate(String storeDate) {
        this.storeDate = storeDate;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getNote () { return note; }

    public void setNote (String note) { this.note = note; }

    public String getUserEmail () { return userEmail; }

    public void setUserEmail (String userEmail) {this.userEmail = userEmail; }
}