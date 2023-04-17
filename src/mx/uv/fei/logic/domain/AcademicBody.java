package mx.uv.fei.logic.domain;

import java.util.Objects;

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
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AcademicBody)) {
            return false;
        }
            AcademicBody academicBody = (AcademicBody) o;
            return (this.academicBodyHeadID == academicBody.getAcademicBodyHeadID()) && (this.description.equals(academicBody.getDescription()));
    }

    @Override
    public String toString() {
        return "ID Cuerpo Académico: " + this.academicBodyID + " ID Encargado: " + this.academicBodyHeadID + " Descripcion: " + this.description;
    }
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + this.academicBodyHeadID;
        hash = 97 * hash + Objects.hashCode(this.description);
        return hash;
    }
}
