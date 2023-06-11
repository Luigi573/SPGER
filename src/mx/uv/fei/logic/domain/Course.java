package mx.uv.fei.logic.domain;

public class Course {
    private int block;
    private String name;
    private int nrc;
    private Professor professor;
    private ScholarPeriod scholarPeriod;
    private int section;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getNrc() {
        return this.nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public ScholarPeriod getScholarPeriod() {
        return scholarPeriod;
    }

    public void setScholarPeriod(ScholarPeriod scholarPeriod) {
        this.scholarPeriod = scholarPeriod;
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

    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Professor getProfessor() {
        return professor;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Course course){
            return this.nrc == course.nrc  && scholarPeriod.equals(course.scholarPeriod)  && this.section == course.section &&
               this.block == course.block;
        }        

        return false;
    }
    
}
