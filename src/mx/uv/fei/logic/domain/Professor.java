package mx.uv.fei.logic.domain;

import mx.uv.fei.logic.domain.statuses.ProfessorStatus;

public class Professor extends User{
    int staffNumber;
    String status;
    
    public void setStaffNumber(int staffNumber){
        this.staffNumber = staffNumber;
    }

    public int getStaffNumber(){
        return staffNumber;
    }
    
    public void setStatus(String status){
        this.status = status;
    }

    public String getStatus(){
        return this.status;
    }

}
