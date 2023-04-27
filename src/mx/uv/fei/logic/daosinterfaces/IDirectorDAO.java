package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Director;

public interface IDirectorDAO {
    public void addDirectorToDatabase(Director director);
    public ArrayList<Director> getDirectorsFromDatabase();
    public ArrayList<Director> getSpecifiedDirectorsFromDatabase(String directorName);
    public Director getDirectorFromDatabase(String directorName);
}
