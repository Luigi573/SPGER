package mx.uv.fei.logic.domain;

import java.sql.Date;

public class ScholarPeriod {
    private int scholarPeriodId;
    private Date endDate;
    private Date startDate;

    public int getScholarPeriodId() {
        return scholarPeriodId;
    }

    public void setScholarPeriodId(int scholarPeriodId) {
        this.scholarPeriodId = scholarPeriodId;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @Override
    public String toString() {
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

        ScholarPeriod s = (ScholarPeriod) scholarPeriod;

        return this.startDate.equals(s.startDate) &&
                this.endDate.equals(s.endDate);
    }

}
