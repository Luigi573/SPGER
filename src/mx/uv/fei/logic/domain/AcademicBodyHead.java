package mx.uv.fei.logic.domain;

public class AcademicBodyHead extends Director{
    private int academicBodyHeadid;
   
    public void setAcademicBodyHeadId(int academicBodyHeadid){
        this.academicBodyHeadid = academicBodyHeadid;
    }
    public int getAcademicBodyHeadid() {
        return academicBodyHeadid;
    }
    
    @Override
    public boolean equals(Object obj){
        if(obj instanceof AcademicBodyHead academicBodyHead){
            return this.staffNumber == academicBodyHead.getStaffNumber();
        }
        
        return false;
    }
}
