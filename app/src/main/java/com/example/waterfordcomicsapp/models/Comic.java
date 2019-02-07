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


    public Comic() {}

    public Comic(String comicId, String Title, String image,String extension, String issueNumber, String storeDate, String price)
    {
        this.comicId = comicId;
        this.comicTitle = Title;
        this.image = image;
        this.extension = extension;
        this.issueNumber = issueNumber;
        this.storeDate = storeDate;
        this.price = price;
    }

    @Override
    public String toString() {
        return comicId + ", " + comicTitle + ", " + image + ", " + extension + ", " + issueNumber
                + ", " + storeDate + ", " + price;
    }
}