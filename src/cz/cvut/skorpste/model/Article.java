package cz.cvut.skorpste.model;

import java.util.Date;

/**
 * Created by stopka on 14.3.14.
 */
public class Article {
    private long id;
    private String title;
    private String text;
    private String author;
    private Date date;
    public Article(long id, String title, String text, String author, Date date) {
        this.id=id;
        this.title=title;
        this.text = text;
        this.author=author;
        this.date=date;
    }

    public long getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getText(){
        return text;
    }

    public String getAuthor(){
        return author;
    }

    public Date getDate(){
        return date;
    }


}
