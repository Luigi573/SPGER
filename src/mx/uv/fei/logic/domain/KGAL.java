package mx.uv.fei.logic.domain;

public class KGAL {
    private int kgalID;
    private String description;
    
    public void setKgalID(int kgalID) {
        this.kgalID = kgalID;
    }
    
    public int getKgalID() {
        return this.kgalID;
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