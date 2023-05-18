package mx.uv.fei.logic.domain;

public class Course {
    private int nrc;
    private int idScholarPeriod;
    private String eeName;
    private int section;
    private int block;
    private int personalNumber; //cambiar a Profesor

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

    @Override
    public boolean equals(Object course) {
        if (this == course)
            return true;
        if (course == null)
            return false;
        if (this.getClass() != course.getClass())
        return false;

        Course c = (Course)course;
 
        // Checking only if attribute- name
        // and age is same and ignoring breed

        return this.nrc == c.nrc  &&
               this.idScholarPeriod == c.idScholarPeriod &&
               this.eeName.equals(c.eeName) &&
               this.section == c.section &&
               this.block == c.block &&
               this.personalNumber == c.personalNumber;
    }
    
}
