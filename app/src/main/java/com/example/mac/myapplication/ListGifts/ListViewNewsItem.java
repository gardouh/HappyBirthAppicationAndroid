package com.example.mac.myapplication.ListGifts;

/**
 * Created by mac on 07/09/2017.
 */

public class ListViewNewsItem {



    private int    mNewsID;
    private String mNewsTitle;
    private String mNewsDate;
    private String mNewsBody;

    public ListViewNewsItem(int mNewsID, String mNewsTitle, String mNewsBody) {
        this.mNewsID = mNewsID;
        this.mNewsTitle = mNewsTitle;

        this.mNewsBody = mNewsBody;
    }

    public int getmNewsID() {
        return mNewsID;
    }

    public void setmNewsID(int mNewsID) {
        this.mNewsID = mNewsID;
    }

    public String getmNewsTitle() {
        return mNewsTitle;
    }

    public void setmNewsTitle(String mNewsTitle) {
        this.mNewsTitle = mNewsTitle;
    }

    public String getmNewsDate() {
        return mNewsDate;
    }

    public void setmNewsDate(String mNewsDate) {
        this.mNewsDate = mNewsDate;
    }

    public String getmNewsBody() {
        return mNewsBody;
    }

    public void setmNewsBody(String mNewsBody) {
        this.mNewsBody = mNewsBody;
    }

}
