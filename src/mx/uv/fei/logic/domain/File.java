package mx.uv.fei.logic.domain;

public class File {
    private int fileId;
    private String filePath;

    public int getFileId() {
        return fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof File)) {
            return false;
        }
        File file = (File) o;
        return this.filePath.equals(file.filePath);
    }

    @Override
    public String toString() {
        return "FileId: " + this.fileId + " FilePath: " + this.filePath;
    }
}