package mx.uv.fei.logic.domain;

public class DegreeBoss extends Professor{
    private int idDegreeBoss;

    public int getIdDegreeBoss() {
        return this.idDegreeBoss;
    }

    public void setIdDegreeBoss(int idDegreeBoss) {
        this.idDegreeBoss = idDegreeBoss;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof DegreeBoss degreeBoss){
            return this.staffNumber == degreeBoss.getStaffNumber();
        }
        
        return false;
    }
}