package mx.uv.fei.logic.domain;

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
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Professor professor){
            return staffNumber == professor.getStaffNumber();
        }
        
        return false;
    }
}
