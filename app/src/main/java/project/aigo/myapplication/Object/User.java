package project.aigo.myapplication.Object;

public class User {

    private String userID;
    private String name;
    private String email;
    private String password;
    private String photo;
    private String phone;
    private String birthDate;
    private String address;
    private String gender;
    private String sport_branch;

    public User ( String userID , String name , String email , String photo , String phone , String birthDate , String address , String gender , String sport_branch ) {
        this.userID = userID;
        this.name = name;
        this.email = email;
        this.photo = photo;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.gender = gender;
        this.sport_branch = sport_branch;
    }

    public String getUserID () {
        return userID;
    }

    public void setUserID ( String userID ) {
        this.userID = userID;
    }

    public String getName () {
        return name;
    }

    public void setName ( String name ) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail ( String email ) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword ( String password ) {
        this.password = password;
    }

    public String getPhoto () {
        return photo;
    }

    public void setPhoto ( String photo ) {
        this.photo = photo;
    }

    public String getPhone () {
        return phone;
    }

    public void setPhone ( String phone ) {
        this.phone = phone;
    }

    public String getBirthDate () {
        return birthDate;
    }

    public void setBirthDate ( String birthDate ) {
        this.birthDate = birthDate;
    }

    public String getAddress () {
        return address;
    }

    public void setAddress ( String address ) {
        this.address = address;
    }

    public String getGender () {
        return gender;
    }

    public void setGender ( String gender ) {
        this.gender = gender;
    }

    public String getSport_branch () {
        return sport_branch;
    }

    public void setSport_branch ( String sport_branch ) {
        this.sport_branch = sport_branch;
    }
}
