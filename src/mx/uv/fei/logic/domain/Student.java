package mx.uv.fei.logic.domain;

public class Student extends User{
    private String matricle;
    private String status;

    
    public void setMatricle(String matricle){
        this.matricle = matricle;
    }
    public String getMatricule(){
        return matricle;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }
}
