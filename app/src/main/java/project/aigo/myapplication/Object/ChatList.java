package project.aigo.myapplication.Object;

public class ChatList {

    private String id;
    private String name;
    private String lastTimeChat;
    private String lastChat;
    private String photo;
    private String notificationKey;

    public ChatList ( String id , String name , String lastTimeChat , String lastChat , String photo , String notificationKey ) {
        this.id = id;
        this.name = name;
        this.lastTimeChat = lastTimeChat;
        this.lastChat = lastChat;
        this.photo = photo;
        this.notificationKey = notificationKey;
    }

    public String getId () {
        return id;
    }

    public void setId ( String id ) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getLastTimeChat () {
        return lastTimeChat;
    }

    public void setLastTimeChat ( String lastTimeChat ) {
        this.lastTimeChat = lastTimeChat;
    }

    public String getLastChat () {
        return lastChat;
    }

    public void setLastChat ( String lastChat ) {
        this.lastChat = lastChat;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto ( String photo ) {
        this.photo = photo;
    }

    public String getNotificationKey () {
        return notificationKey;
    }

    public void setNotificationKey ( String notificationKey ) {
        this.notificationKey = notificationKey;
    }
}
