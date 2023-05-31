package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.Research;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchesReportDAO {
    public abstract ArrayList<Research> getResearches (String title, String query) throws DataRetrievalException;
    public abstract ArrayList<Research> getValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getNotValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getValidatedAndNotValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<Research> getSelectedResearches (ArrayList<String> selectedResearchesTitles) throws DataRetrievalException;
}
