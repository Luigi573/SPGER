package mx.uv.fei.logic.domain;

public class Course {
    private int nrc;
    private int idScholarPeriod;
    private String eeName;
    private String section;
    private int block;
    private int personalNumber;
    private String status;

    public int getNrc() {
        return this.nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getIdScholarPeriod() {
        return idScholarPeriod;
    }

    public void setIdScholarPeriod(int idScholarPeriod) {
        this.idScholarPeriod = idScholarPeriod;
    }

    public String getEEName() {
        return this.eeName;
    }

    public void setEEName(String eeName) {
        this.eeName = eeName;
    }

    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public int getBlock() {
        return this.block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getPersonalNumber() {
        return this.personalNumber;
    }

    public void setPersonalNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
