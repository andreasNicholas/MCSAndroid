package project.aigo.myapplication.Object;

import android.graphics.Bitmap;

import java.util.Vector;

public class News {

    private String id;
    private String title;
    private String description;
    private String imageSrc;
    private String viewsCount;

    public News () {

    }

    public News ( String id , String title , String description , String imageSrc , String viewsCount ) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageSrc = imageSrc;
        this.viewsCount = viewsCount;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle ( String title ) {
        this.title = title;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription ( String description ) {
        this.description = description;
    }

    public String getImageSrc () {
        return imageSrc;
    }

    public void setImageSrc ( String imageSrc ) {
        this.imageSrc = imageSrc;
    }

    public String getViewsCount () {
        return viewsCount;
    }

    public void setViewsCount ( String viewsCount ) {
        this.viewsCount = viewsCount;
    }
}
