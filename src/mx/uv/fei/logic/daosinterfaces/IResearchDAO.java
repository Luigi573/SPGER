package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataWritingException;

public interface IResearchDAO {
    public int addResearch(ResearchProject research) throws DataWritingException;
    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getResearchProjectList() throws DataRetrievalException;
    public int modifyResearch(ResearchProject research) throws DataWritingException;
    public boolean assertResearch(ResearchProject research);
    public void validateResearch(ResearchProject researchProject) throws DataWritingException;
}
