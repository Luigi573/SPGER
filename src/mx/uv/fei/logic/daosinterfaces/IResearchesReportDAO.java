package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchesReportDAO {
    public abstract ArrayList<Research> getResearchesFromDatabase (String title, String query) throws DataRetrievalException;
    public abstract ArrayList<Research> getValidatedResearchesFromDatabase (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getNotValidatedResearchesFromDatabase (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getValidatedAndNotValidatedResearchesFromDatabase (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getSelectedResearchesFromDatabase (ArrayList<String> selectedResearchesTitles) throws DataRetrievalException;
}
