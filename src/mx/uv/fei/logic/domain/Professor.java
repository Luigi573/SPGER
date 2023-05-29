package mx.uv.fei.logic.domain;

public class Professor extends User{
    protected int staffNumber;
    protected String status;
    
    public void setStaffNumber(int staffNumber){
        this.staffNumber = staffNumber;
    }
    public int getStaffNumber(){
        return staffNumber;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getStatus(){
        return this.status;
    }
    public boolean isAssignable(Class cls){
        return this.isAssignable(cls);
    }

    @Override
    public boolean equals(Object professor) {
        if (this == professor)
            return true;
        if (professor == null)
            return false;
        if (this.getClass() != professor.getClass())
        return false;

        Professor p = (Professor)professor;

        return this.name.equals(p.name) &&
               this.firstSurname.equals(p.firstSurname)  &&
               this.secondSurname.equals(p.secondSurname) &&
               this.emailAddress.equals(p.emailAddress) &&
               this.alternateEmail.equals(p.alternateEmail) &&
               this.phoneNumber.equals(p.phoneNumber) &&
               this.staffNumber == p.staffNumber &&
               this.status.equals(p.status);
    }

    
}
