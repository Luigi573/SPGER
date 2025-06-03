package mx.uv.fei.logic.domain;

public class Course {
    private int nrc;
    private int scholarPeriodId;
    private int staffNumber;
    private int block;
    private String name;
    private int section;
    private String status;

    public int getNrc() {
        return nrc;
    }

    public void setNrc(int nrc) {
        this.nrc = nrc;
    }

    public int getScholarPeriodId() {
        return scholarPeriodId;
    }

    public void setScholarPeriodId(int scholarPeriodId) {
        this.scholarPeriodId = scholarPeriodId;
    }

    public int getStaffNumber() {
        return staffNumber;
    }

    public void setStaffNumber(int staffNumber) {
        this.staffNumber = staffNumber;
    }

    public int getBlock() {
        return block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Course course) {
            return this.nrc == course.nrc && this.scholarPeriodId == course.scholarPeriodId
                    && this.staffNumber == course.staffNumber &&
                    this.block == course.block && this.name == course.name && this.section == course.section
                    && this.status == course.status;
        }

        return false;
    }
}
