package com.example.models;

import java.io.Serializable;

public class Comic implements Serializable
{
    public String comicId;
    public String comicName;
    public String image;
    public String issueNumber;
    public String storeDate;
    public String description;


    public Comic() {}

    public Comic(String name, String image, String issueNumber, String storeDate, String description)
    {
        this.comicName = name;
        this.image = image;
        this.issueNumber = issueNumber;
        this.storeDate = storeDate;
        this.description = description;
    }

    @Override
    public String toString() {
        return comicName + ", " + image + ", " + issueNumber
                + ", " + storeDate + ", " + description;
    }
}