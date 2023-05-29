package mx.uv.fei.logic.domain;

public class Course {
    private int nrc;
    private int idScholarPeriod;
    private String name;
    private int section;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSection() {
        return this.section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getBlock() {
        return this.block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public int getStaffNumber() {
        return this.personalNumber;
    }

    public void setStaffNumber(int personalNumber) {
        this.personalNumber = personalNumber;
    }

    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
}
