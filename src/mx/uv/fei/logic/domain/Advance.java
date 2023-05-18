
package mx.uv.fei.logic.domain;

public class Advance {
    private int advanceID;
    private String studentID;
    private int directorID;
    private String title;
    private String comments;

    public Advance() {}
    
    public Advance(String studentID, int directorID, String title, String comments) {
        this.studentID = studentID;
        this.directorID = directorID;
        this.title = title;
        this.comments = comments;
    }
    
    public void setAdvanceID(int advanceID) {
        this.advanceID = advanceID;
    }
    
    public void setMatricle(String studentID) {
        this.studentID = studentID;
    }
    
    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public int getAdvanceID() {
        return this.advanceID;
    }
    
    public String getMatricule() {
        return this.studentID;
    }
    
    public int getDirectorID() {
        return this.directorID;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getComments() {
        return this.comments;
    }
    
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Advance)) {
            return false;
        }
            Advance advance = (Advance) o;
            return (this.studentID == advance.getMatricule()) && (this.directorID == advance.getDirectorID() && (this.title.equals(advance.getTitle())) && (this.comments.equals(advance.getComments())));
    }

    @Override
    public String toString() {
        return "ID Avance: " + this.advanceID + " ID Estudiante: " + this.studentID + " ID Director: " + this.directorID + " Titulo: " + this.title + " Comentarios: " + this.comments;
    }
}
