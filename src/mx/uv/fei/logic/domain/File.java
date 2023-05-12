/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mx.uv.fei.logic.domain;

/**
 *
 * @author Jesús Manuel
 */
public class File {
    private int idArchivo;
    private String filePath;
    
    public void setIdArchivo(int idArchivo) {
        this.idArchivo = idArchivo;
    }
    
    public void setPath(String filePath) {
        this.filePath = filePath;
    }
    
    public int getIdArchivo() {
        return this.idArchivo;
    }
    
    public String getFilePath() {
        return this.filePath;
    }
}
