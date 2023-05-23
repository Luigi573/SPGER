package mx.uv.fei.logic.domain;

public class ScholarPeriod {
    private String endDate;
    private int IdScholarPeriod;
    private String startDate;

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public int getIdScholarPeriod() {
        return this.IdScholarPeriod;
    }

    public void setIdScholarPeriod(int idScholarPeriod) {
        IdScholarPeriod = idScholarPeriod;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString(){
        return this.startDate + " " + this.endDate;
    }
    
}
