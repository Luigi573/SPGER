package mx.uv.fei.logic.domain;

public class AcademicBodyHead extends Professor {
    private int academicBodyHeadId;

    public int getAcademicBodyHeadid() {
        return academicBodyHeadId;
    }

    public void setAcademicBodyHeadid(int academicBodyHeadId) {
        this.academicBodyHeadId = academicBodyHeadId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof AcademicBodyHead academicBodyHead) {
            return this.staffNumber == academicBodyHead.getStaffNumber();
        }

        return false;
    }
}
