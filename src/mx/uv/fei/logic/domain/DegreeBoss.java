package mx.uv.fei.logic.domain;

public class DegreeBoss extends Professor{
    private int idDegreeBoss;

    public int getIdDegreeBoss() {
        return idDegreeBoss;
    }
    public void setIdDegreeBoss(int idDegreeBoss) {
        this.idDegreeBoss = idDegreeBoss;
    }

    @Override
    public boolean equals(Object degreeBoss) {
        if (this == degreeBoss)
            return true;
        if (degreeBoss == null)
            return false;
        if (this.getClass() != degreeBoss.getClass())
        return false;

        DegreeBoss db = (DegreeBoss)degreeBoss;

        return this.name.equals(db.name) &&
               this.firstSurname.equals(db.firstSurname)  &&
               this.secondSurname.equals(db.secondSurname) &&
               this.emailAddress.equals(db.emailAddress) &&
               this.alternateEmail.equals(db.alternateEmail) &&
               this.phoneNumber.equals(db.phoneNumber) &&
               this.staffNumber == db.staffNumber &&
               this.status.equals(db.status);
    }

}
