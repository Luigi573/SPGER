package mx.uv.fei.logic.domain;

public class Student extends User{
    private String matricule;
    private String status;

    
    public void setMatricle(String matricule){
        this.matricule = matricule;
    }
    public String getMatricule(){
        return matricule;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
}
