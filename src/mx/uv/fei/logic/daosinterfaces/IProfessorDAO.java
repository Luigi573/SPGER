package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public interface IProfessorDAO {
    public int addProfessor(Professor professor) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public int modifyProfessorData(Professor professor) throws DataInsertionException, DuplicatedPrimaryKeyException;
    public ArrayList<Professor> getProfessors() throws DataRetrievalException;
    public ArrayList<Professor> getSpecifiedProfessors(String professorName) throws DataRetrievalException;
    public Professor getProfessor(int personalNumber) throws DataRetrievalException;
}
