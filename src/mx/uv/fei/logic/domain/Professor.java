package mx.uv.fei.logic.domain;

public class Professor extends User{
    protected int idProfessor;
    protected String personalNumber;
    protected String status;

    public int getIdProfessor() {
        return this.idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getPersonalNumber() {
        return this.personalNumber;
    }

    public void setPersonalNumber(String personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}
