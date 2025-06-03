package mx.uv.fei.logic.domain;

public class Director extends Professor {
    private int directorId;

    public void setDirectorId(int directorId) {
        this.directorId = directorId;
    }

    public int getDirectorId() {
        return directorId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Director director) {
            return director.getStaffNumber() == this.staffNumber;
        }

        return false;
    }
}