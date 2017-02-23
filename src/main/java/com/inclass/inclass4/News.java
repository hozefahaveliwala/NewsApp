

package com.inclass.inclass4;

import java.text.SimpleDateFormat;
import java.util.Date;


public class News {

    String title, author, description, urlToImage ;
Date publishAt;
    public String getTitle() {
        return title;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf=new SimpleDateFormat();
        return  title + "\n" +
                "Author: " + author + "\n" +
                "Published on: " + sdf.format(publishAt) + "\n\n" +
                "Description: \n" + description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public void setUrlToImage(String urlToImage) {
        this.urlToImage = urlToImage;
    }

    public Date getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(Date publishAt) {
        this.publishAt = publishAt;
    }
}
