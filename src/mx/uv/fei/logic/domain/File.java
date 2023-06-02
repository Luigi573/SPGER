package mx.uv.fei.logic.domain;

public class File {
    private int fileId;
    private String filePath;
    
    public void setFileId(int fileId) {
        this.fileId = fileId;
    }
    
    public void setPath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getFileId() {
        return this.fileId;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
}
