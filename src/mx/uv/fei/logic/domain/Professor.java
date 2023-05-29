package mx.uv.fei.logic.domain;

import mx.uv.fei.logic.domain.statuses.ProfessorStatus;

public class Professor extends User{
    int staffNumber;
    ProfessorStatus status;
    
    public void setStaffNumber(int staffNumber){
        this.staffNumber = staffNumber;
    }

    public int getStaffNumber(){
        return staffNumber;
    }
    
    public void setStatus(ProfessorStatus status){
        this.status = status;
    }

    public ProfessorStatus getStatus(){
        return this.status;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Professor professor){
            return staffNumber == professor.getStaffNumber();
        }
        
        return false;
    }
}
