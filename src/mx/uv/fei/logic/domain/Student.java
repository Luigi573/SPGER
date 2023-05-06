package mx.uv.fei.logic.domain;

public class Student extends User{
    private int idStudent;
    private String matricle;
    private String status;

    public int getIdUser() {
        return super.idUser;
    }

    public void setIdUser(int idUser) {
        super.idUser = idUser;
    }

    public String getName() {
        return super.name;
    }

    public void setName(String name) {
        super.name = name;
    }

    public String getFirstSurname() {
        return super.firstSurname;
    }

    public void setFirstSurname(String firstSurname) {
        super.firstSurname = firstSurname;
    }

    public String getSecondSurname() {
        return super.secondSurname;
    }

    public void setSecondSurname(String secondSurname) {
        super.secondSurname = secondSurname;
    }

    public String getEmailAddress() {
        return super.emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        super.emailAddress = emailAddress;
    }

    public String getPassword() {
        return super.password;
    }

    public void setPassword(String password) {
        super.password = password;
    }

    public String getAlternateEmail() {
        return super.alternateEmail;
    }

    public void setAlternateEmail(String alternateEmail) {
        super.alternateEmail = alternateEmail;
    }

    public String getPhoneNumber() {
        return super.phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        super.phoneNumber = phoneNumber;
    }

    public int getIdStudent() {
        return this.idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public String getMatricle() {
        return this.matricle;
    }

    public void setMatricle(String matricle) {
        this.matricle = matricle;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
