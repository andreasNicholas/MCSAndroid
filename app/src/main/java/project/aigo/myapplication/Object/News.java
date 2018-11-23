package project.aigo.myapplication.Object;

import java.util.Vector;

public class News {
    public static Vector<News> newsList = new Vector<News>();

    private String news_title;
    private int image_id;
    private int video_id;
    private String news_content;

    public News(String news_title, int image_id, int video_id, String news_content) {
        this.news_title = news_title;
        this.image_id = image_id;
        this.video_id = video_id;
        this.news_content = news_content;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public int getImage_id() {
        return image_id;
    }

    public void setImage_id(int image_id) {
        this.image_id = image_id;
    }

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public String getNews_content() {
        return news_content;
    }

    public void setNews_content(String news_content) {
        this.news_content = news_content;
    }
}
