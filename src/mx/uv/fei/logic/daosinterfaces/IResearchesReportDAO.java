package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchesReportDAO {
    public abstract ArrayList<ResearchProject> getResearches (String title, String query) throws DataRetrievalException;
    public abstract ArrayList<ResearchProject> getValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<ResearchProject> getNotValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<ResearchProject> getValidatedAndNotValidatedResearches (String title) throws DataRetrievalException;
    public abstract ArrayList<ResearchProject> getSelectedResearches (ArrayList<String> selectedResearchesTitles) throws DataRetrievalException;
}
