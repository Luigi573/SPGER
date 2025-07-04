package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchProjectDAO {
    public int addResearchProject(ResearchProject researchProject) throws DataInsertionException;

    public ArrayList<ResearchProject> getResearchProjectsList() throws DataRetrievalException;

    public ArrayList<ResearchProject> getDirectorResearchProjects(int staffNumber) throws DataRetrievalException;

    public ResearchProject getStudentResearchProject(String matricle) throws DataRetrievalException;

    public ArrayList<ResearchProject> getCourseResearchProjects(int NRC) throws DataRetrievalException;

    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException;

    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName) throws DataRetrievalException;

    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;

    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;

    public int modifyResearchProject(ResearchProject researchProject) throws DataInsertionException;

    public void validateResearchProject(ResearchProject researchProject) throws DataInsertionException;
}
