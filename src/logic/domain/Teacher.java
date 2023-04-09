package logic.domain;
import logic.domain.statuses.ProfessorStatus;

public class Teacher extends User{
    String id;
    ProfessorStatus status;
    
    public Teacher(String name, String firstSurname, String secondSurname, String emailAddress, String password){
        super(name, firstSurname, secondSurname, emailAddress, password);
    }
    public void setId(String id){
        this.id = id;
    }
    public void setStatus(ProfessorStatus status){
        this.status = status;
    }
    public String getId(){
        return id;
    }
    public ProfessorStatus getStatus(){
        return status;
    }
}
