package mx.uv.fei.logic.domain;

public class Professor extends User{
    protected int personalNumber;
    protected String status;

    public int getPersonalNumber() {
        return this.personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus(){
        return this.status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}
