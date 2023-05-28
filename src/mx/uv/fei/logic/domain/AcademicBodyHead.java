package mx.uv.fei.logic.domain;

public class AcademicBodyHead extends Professor{
    private int academicBodyHeadid;
    
    public void setAcademicBodyHeadId(int academicBodyHeadid){
        this.academicBodyHeadid = academicBodyHeadid;
    }
    public int getacademicBodyHeadId() {
        return academicBodyHeadid;
    }

    @Override
    public boolean equals(Object academicBodyHead) {
        if (this == academicBodyHead)
            return true;
        if (academicBodyHead == null)
            return false;
        if (this.getClass() != academicBodyHead.getClass())
        return false;

        AcademicBodyHead abh = (AcademicBodyHead)academicBodyHead;

        return this.name.equals(abh.name) &&
               this.firstSurname.equals(abh.firstSurname)  &&
               this.secondSurname.equals(abh.secondSurname) &&
               this.emailAddress.equals(abh.emailAddress) &&
               this.alternateEmail.equals(abh.alternateEmail) &&
               this.phoneNumber.equals(abh.phoneNumber) &&
               this.staffNumber == abh.staffNumber &&
               this.status.equals(abh.status);
    }
}
