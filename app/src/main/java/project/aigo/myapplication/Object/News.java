package project.aigo.myapplication.Object;

import android.graphics.Bitmap;

import java.util.Vector;

public class News {
    public static Vector<News> newsList = new Vector<News>();

    private String news_title;
    private String image_name;
    private String video_name;
    private String news_content;
    private Bitmap image_bitmap;

    public News(String news_title, String image_name, String video_name, String news_content, Bitmap image_bitmap) {
        this.news_title = news_title;
        this.image_name = image_name;
        this.video_name = video_name;
        this.news_content = news_content;
        this.image_bitmap = image_bitmap;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getImage_name() {
        return image_name;
    }

    public void setImage_name(String image_name) {
        this.image_name = image_name;
    }

    public String getVideo_name() {
        return video_name;
    }

    public void setVideo_name(String video_name) {
        this.video_name = video_name;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }

    public Bitmap getImage_bitmap() {
        return image_bitmap;
    }

    public void setImage_bitmap(Bitmap image_bitmap) {
        this.image_bitmap = image_bitmap;
    }
}
