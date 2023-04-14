package mx.uv.fei.logic.domain;

public class AcademicBody {
    private int academicBodyID;
    private int academicBodyHeadID;
    private String description;
    
    public AcademicBody(){}
    
    public AcademicBody(int academicBodyHeadID, String description) {
        this.academicBodyHeadID = academicBodyHeadID;
        this.description = description;
    }
    
    public void setAcademicBodyID(int academicBodyID) {
        this.academicBodyID = academicBodyID;
    }
    
    public void setAcademicBodyHeadID(int academicBodyHeadID) {
        this.academicBodyHeadID = academicBodyHeadID;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getAcademicBodyID() {
        return this.academicBodyID;
    }
    
    public int getAcademicBodyHeadID() {
        return this.academicBodyHeadID;
    }
    
    public String getDescription() {
        return this.description;
    }
}
