package jp.oumimaiko.wordmemo;

import java.io.Serializable;
import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WordsModel extends RealmObject implements Serializable {

    @PrimaryKey
    int id;

    private Date date;
    private String title;  // addressカラム
    private String description;  // nameカラム

    // getter setter...


    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Date getDate(){ return date;}

    public void setDate(Date date) { this.date = date; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

}