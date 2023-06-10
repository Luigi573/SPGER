package mx.uv.fei.logic.domain;

import java.sql.Date;

public class ScholarPeriod {
    private Date endDate;
    private Date startDate;
    private int ScholarPeriodId;

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    
    public Date getEndDate() {
        return this.endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getStartDate() {
        return this.startDate;
    }
  
    public void setScholarPeriodId(int scholarPeriodId) {
        ScholarPeriodId = scholarPeriodId;
    }
      
    public int getScholarPeriodId() {
        return this.ScholarPeriodId;
    }

    @Override
    public String toString(){
        return this.startDate + " " + this.endDate;
    }

    @Override
    public boolean equals(Object scholarPeriod) {
        if (this == scholarPeriod)
            return true;
        if (scholarPeriod == null)
            return false;
        if (this.getClass() != scholarPeriod.getClass())
        return false;

        ScholarPeriod s = (ScholarPeriod)scholarPeriod;

        return this.startDate.equals(s.startDate) &&
               this.endDate.equals(s.endDate);
    }
    
}
