package project.aigo.myapplication.Object;

public class Chat {

    private String userID;
    private String message;
    private String timeSent;
    private String name;
    private String photo;

    public Chat ( String userID , String message , String timeSent , String name , String photo ) {
        this.userID = userID;
        this.message = message;
        this.timeSent = timeSent;
        this.name = name;
        this.photo = photo;
    }

    public String getUserID () {
        return userID;
    }

    public void setUserID ( String userID ) {
        this.userID = userID;
    }

    public String getMessage () {
        return message;
    }

    public void setMessage ( String message ) {
        this.message = message;
    }

    public String getTimeSent () {
        return timeSent;
    }

    public void setTimeSent ( String timeSent ) {
        this.timeSent = timeSent;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto ( String photo ) {
        this.photo = photo;
    }
}
