package mx.uv.fei.logic.domain;

public class Student extends User{
    private String matricle;

    public Student() {
        super();
    }

    public Student(String name, String firstSurname, String secondSurname, String emailAddress, String password) {
        super(name, firstSurname, secondSurname, emailAddress, password);
    }

    public String getMatricle() {
        return matricle;
    }

    public void setMatricle(String matricle) {
        this.matricle = matricle;
    }
    
}
