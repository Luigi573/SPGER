
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
    
    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }
    
    public void setDirectorID(int directorID) {
        this.directorID = directorID;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public void setComment(String comments) {
        this.comments = comments;
    }
    
    public int getAdvanceID() {
        return this.advanceID;
    }
    
    public String getStudentID() {
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
}
