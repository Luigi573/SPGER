package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IProfessorDAO {
    public void addProfessor (Professor professor) throws DataInsertionException;
    public void modifyProfessorData(Professor newProfessorData, Professor originalProfessorData) throws DataInsertionException;
    public ArrayList<Professor> getProfessors() throws DataRetrievalException;
    public ArrayList<Professor> getSpecifiedProfessors(String professorName) throws DataRetrievalException;
    public Professor getProfessor(int staffNumber) throws DataRetrievalException;
}
