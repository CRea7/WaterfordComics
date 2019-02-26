package com.example.waterfordcomicsapp.models;


public class FavouriteComics {
    public String comicId;
    public String comicTitle;

    public String issueNumber;

    public FavouriteComics() {
    }

    public FavouriteComics( String comicId, String issueNumber,String comicTitle) {
        this.comicId = comicId;
        this.comicTitle = comicTitle;
        this.issueNumber = issueNumber;
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

    public String getIssueNumber() {
        return issueNumber;
    }

    public void setIssueNumber(String issueNumber) {
        this.issueNumber = issueNumber;
    }

    @Override
    public String toString() {
        return "FavouriteComics{" +
                "comicId='" + comicId + '\'' +
                ", comicTitle='" + comicTitle + '\'' +
                ", issueNumber='" + issueNumber + '\'' +
                '}';
    }
}