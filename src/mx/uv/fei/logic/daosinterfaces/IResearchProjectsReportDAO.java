package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchProjectsReportDAO {
    public abstract ArrayList<ResearchProject> getResearchProjects(String title) throws DataRetrievalException;

    public abstract ArrayList<ResearchProject> getValidatedResearchProjects(String title) throws DataRetrievalException;

    public abstract ArrayList<ResearchProject> getNotValidatedResearchProjects(String title) throws DataRetrievalException;

    public abstract ArrayList<ResearchProject> getValidatedAndNotValidatedResearchProjects(String title)
            throws DataRetrievalException;

    public abstract ArrayList<ResearchProject> getSelectedResearchProjects(ArrayList<String> selectedResearchesTitles)
            throws DataRetrievalException;
}
