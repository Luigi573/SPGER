package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Professor;

public interface IProfessorDAO {
    public void addProfessorToDatabase(Professor professor);
    public void modifyProfessorDataFromDatabase(Professor newProfessorData, Professor originalProfessorData);
    public ArrayList<Professor> getProfessorsFromDatabase();
    public ArrayList<Professor> getSpecifiedProfessorsFromDatabase(String professorName);
    public Professor getProfessorFromDatabase(int personalNumber);
}
