package mx.uv.fei.logic.domain;

public class ReceptionalWork {
    private int receptionalWorkID;
    private int researchID;
    private String name;
    private String description;
    
    public ReceptionalWork() {}
    
    public ReceptionalWork(int researchID, String name, String description) {
        this.researchID = researchID;
        this.name = name;
        this.description = description;
    }
    
    public void setReceptionalWorkID(int receptionalWorkID) {
        this.receptionalWorkID = receptionalWorkID;
    }
    
    public void setResearchID(int researchID) {
        this.researchID = researchID;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getReceptionalWorkID() {
        return this.receptionalWorkID;
    }
    
    public int getResearchID() {
        return this.researchID;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
}
