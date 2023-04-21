package mx.uv.fei.logic.domain;

public abstract class User {
    String name;
    String firstSurname;
    String secondSurname;
    String emailAddress;
    String password;
    String alternateEmail;
    String phoneNumber;
    
    public User(String name, String firstSurname, String secondSurname, String emailAddress, String password){
        this.name = name;
        this.firstSurname = firstSurname;
        this.emailAddress = emailAddress;
        this.password = password;
    }
    
    public void setName(String name){
        this.name = name;
    }

    public void setFirstSurname(String firstSurname){
        this.firstSurname = firstSurname;
    }

    public void setSecondSurname(String secondSurname){
        this.secondSurname = secondSurname;
    }

    public void setEmailAddress(String emailAddress){
        this.emailAddress = emailAddress;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public void setAlternateEmail(String alternateEmail){
        this.alternateEmail = alternateEmail;
    }

    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    public String getName(){
        return name;
    }

    public String getFirstSurname(){
        return firstSurname;
    }

    public String getSecondSurname(){
        return secondSurname;
    }

    public String getEmailAddress(){
        return emailAddress;
    }

    public String getPassword(){
        return password;
    }

    public String getAlternateEmail(){
        return alternateEmail;
    }

    public String getPhoneNumber(){
        return phoneNumber;
    }
}
