package mx.uv.fei.logic.domain;

public class KGAL {
    private int kgalId;
    private String description;

    public void setKgalId(int kgalId) {
        this.kgalId = kgalId;
    }

    public int getKgalId() {
        return this.kgalId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return description;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KGAL)) {
            return false;
        }
        KGAL kgal = (KGAL) o;
        return this.description.equals(kgal.description);
    }
}