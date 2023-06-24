package mx.uv.fei.logic.domain;

public class Director extends Professor{
    private int id;
    
    public void setDirectorId(int id){
        this.id = id;
    }
    public int getDirectorId(){
        return id;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof Director director){
            return director.getStaffNumber() == this.staffNumber;
        }
        
        return false;
    }
}