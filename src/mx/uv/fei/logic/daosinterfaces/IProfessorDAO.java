package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Professor;

public interface IProfessorDAO {
    public void addProfessorToDatabase(Professor professor);
    public ArrayList<Professor> getProfessorsFromDatabase();
}
