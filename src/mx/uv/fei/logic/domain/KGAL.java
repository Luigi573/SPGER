package mx.uv.fei.logic.domain;

public class KGAL {
    public int kgalID;
    public String description;
    
    public void setKaglID(int kgalID) {
        this.kgalID = kgalID;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public int getKAGL_ID() {
        return this.kgalID;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof KGAL)) {
            return false;
        }
            KGAL kgal = (KGAL) o;
            return this.description.equals(kgal.description);
    }
    
    @Override
    public String toString() {
        return "ID LGAC: " + this.kgalID + "  Descripcion: " + this.description;
    }
}
