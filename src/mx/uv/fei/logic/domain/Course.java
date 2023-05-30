package mx.uv.fei.logic.domain;

public class Course {
    private int nrc;
    private ScholarPeriod scholarPeriod;
    private String name;
    private int section;
    private int block;
    private Professor professor; //cambiar a Profesor

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getNrc() {
        return this.nrc;
    }

    public ScholarPeriod getScholarPeriod() {
        return scholarPeriod;
    }

    public void setScholarPeriod(ScholarPeriod period) {
        this.scholarPeriod = period;
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
