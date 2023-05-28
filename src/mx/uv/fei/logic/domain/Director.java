package mx.uv.fei.logic.domain;

public class Director extends Professor{
    private int idDirector;
    
    public void setDirectorId(int idDirector){
        this.idDirector = idDirector;
    }
    public int getDirectorId(){
        return idDirector;
    }

    @Override
    public boolean equals(Object director) {
        if (this == director)
            return true;
        if (director == null)
            return false;
        if (this.getClass() != director.getClass())
        return false;

        Director d = (Director)director;

        return this.name.equals(d.name) &&
               this.firstSurname.equals(d.firstSurname)  &&
               this.secondSurname.equals(d.secondSurname) &&
               this.emailAddress.equals(d.emailAddress) &&
               this.alternateEmail.equals(d.alternateEmail) &&
               this.phoneNumber.equals(d.phoneNumber) &&
               this.staffNumber == d.staffNumber &&
               this.status.equals(d.status);
    }
}