package mx.uv.fei.logic.domain;

public class DegreeBoss extends Professor {
    private int degreeBossId;

    public int getDegreeBossId() {
        return degreeBossId;
    }

    public void setDegreeBossId(int degreeBossId) {
        this.degreeBossId = degreeBossId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof DegreeBoss degreeBoss) {
            return this.staffNumber == degreeBoss.getStaffNumber();
        }

        return false;
    }
}