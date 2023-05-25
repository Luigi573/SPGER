package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IProfessorDAO {
    public void addProfessorToDatabase(Professor professor) throws DataWritingException;
    public void modifyProfessorDataFromDatabase(Professor newProfessorData, Professor originalProfessorData) throws DataWritingException;
    public ArrayList<Professor> getProfessorsFromDatabase() throws DataRetrievalException;
    public ArrayList<Professor> getSpecifiedProfessorsFromDatabase(String professorName) throws DataRetrievalException;
    public Professor getProfessorFromDatabase(int personalNumber) throws DataRetrievalException;
}
