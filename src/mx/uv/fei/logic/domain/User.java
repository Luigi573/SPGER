package mx.uv.fei.logic.domain;

public abstract class User {
    protected int userId;
    protected String name;
    protected String firstSurname;
    protected String secondSurname;
    protected String emailAddress;
    protected String password;
    protected String alternateEmail;
    protected String phoneNumber;
    
    public void setUserId(int userId){
        this.userId = userId;
    }
    public int getUserId(){
        return userId;
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
    
    @Override
    public String toString(){
        return name + " " + firstSurname + " " + secondSurname;
    }
}
