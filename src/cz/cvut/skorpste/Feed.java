package cz.cvut.skorpste;

import java.util.Date;

/**
 * Created by stopka on 14.3.14.
 */
public class Feed {
    private long id;
    private String title;
    private String article;
    private String author;
    private Date date;
    public Feed(long id, String title,String article,String author,Date date) {
        this.id=id;
        this.title=title;
        this.article=article;
        this.author=author;
        this.date=date;
    }

    public long getID(){
        return id;
    }

    public String getTitle(){
        return title;
    }

    public String getArticle(){
        return article;
    }

    public String getAuthor(){
        return author;
    }

    public Date getDate(){
        return date;
    }


}
