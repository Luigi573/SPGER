package mx.uv.fei.logic.domain;

public class File {
    private int fileID;
    private String filePath;
    
    public void setFileID(int idArchivo) {
        this.fileID = idArchivo;
    }
    
    public void setPath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getFileID() {
        return this.fileID;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
}
