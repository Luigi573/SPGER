package mx.uv.fei.logic.domain;

public class Student extends User{
    private String matricule;
    private String status;

    
    public void setMatricle(String matricule){
        this.matricule = matricule;
    }
    public String getMatricule(){
        return matricule;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getStatus() {
        return this.status;
    }

    @Override
    public boolean equals(Object student) {
        if (this == student)
            return true;
        if (student == null)
            return false;
        if (this.getClass() != student.getClass())
        return false;

        Student s = (Student)student;

        return this.name.equals(s.name) &&
               this.firstSurname.equals(s.firstSurname)  &&
               this.secondSurname.equals(s.secondSurname) &&
               this.emailAddress.equals(s.emailAddress) &&
               this.alternateEmail.equals(s.alternateEmail) &&
               this.phoneNumber.equals(s.phoneNumber) &&
               this.matricule.equals(s.matricule) &&
               this.status.equals(s.status);
    }
}
