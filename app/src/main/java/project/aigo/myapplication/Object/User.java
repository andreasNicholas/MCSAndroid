package project.aigo.myapplication.Object;

import java.util.Date;

public class User {

    private String name;
    private String email;
    private String password;
    private String phone;
    private Date birthDate;
    private String address;
    private String gender;

    public User ( String name , String email , String password , String phone , Date birthDate , String address , String gender ) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.birthDate = birthDate;
        this.address = address;
        this.gender = gender;
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

    public String getPhone () {
        return phone;
    }

    public void setPhone ( String phone ) {
        this.phone = phone;
    }

    public Date getBirthDate () {
        return birthDate;
    }

    public void setBirthDate ( Date birthDate ) {
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

}