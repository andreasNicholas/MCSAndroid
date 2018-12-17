package project.aigo.myapplication.Object;

public class ChatList {

    private String id;
    private String name;
    private String lastTimeChat;
    private String lastChat;
    private String photo;

    public ChatList ( String id , String name , String lastTimeChat , String lastChat , String photo ) {
        this.id = id;
        this.name = name;
        this.lastTimeChat = lastTimeChat;
        this.lastChat = lastChat;
        this.photo = photo;
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
}
