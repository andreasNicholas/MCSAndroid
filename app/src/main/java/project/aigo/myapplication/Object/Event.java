package project.aigo.myapplication.Object;

import java.util.Vector;

public class Event {
    public static Vector<Event> eventList = new Vector<>();

    private String id;
    private String event_name;
    private String event_start_datetime;
    private String event_end_datetime;
    private String event_description;
    private String event_image_path;
    private String views_count;
    private String created_by;

    public Event () {
    }

    public Event ( String id , String event_name , String event_start_datetime , String event_end_datetime , String event_description , String event_image_path , String views_count , String created_by ) {
        this.id = id;
        this.event_name = event_name;
        this.event_start_datetime = event_start_datetime;
        this.event_end_datetime = event_end_datetime;
        this.event_description = event_description;
        this.event_image_path = event_image_path;
        this.views_count = views_count;
        this.created_by = created_by;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getEvent_name () {
        return event_name;
    }

    public void setEvent_name ( String event_name ) {
        this.event_name = event_name;
    }

    public String getEvent_start_datetime () {
        return event_start_datetime;
    }

    public void setEvent_start_datetime ( String event_start_datetime ) {
        this.event_start_datetime = event_start_datetime;
    }

    public String getEvent_end_datetime () {
        return event_end_datetime;
    }

    public void setEvent_end_datetime ( String event_end_datetime ) {
        this.event_end_datetime = event_end_datetime;
    }

    public String getEvent_description () {
        return event_description;
    }

    public void setEvent_description ( String event_description ) {
        this.event_description = event_description;
    }

    public String getEvent_image_path () {
        return event_image_path;
    }

    public void setEvent_image_path ( String event_image_path ) {
        this.event_image_path = event_image_path;
    }

    public String getViews_count () {
        return views_count;
    }

    public void setViews_count ( String views_count ) {
        this.views_count = views_count;
    }

    public String getCreated_by () {
        return created_by;
    }

    public void setCreated_by ( String created_by ) {
        this.created_by = created_by;
    }
}