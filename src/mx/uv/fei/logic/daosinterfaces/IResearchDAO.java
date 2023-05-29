package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IResearchDAO {
<<<<<<< HEAD
    public int addResearch(ResearchProject research) throws DataInsertionException;
=======
    public int addResearch(ResearchProject research) throws DataWritingException;
    public ArrayList<ResearchProject> getSpecifiedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedValidatedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;
    public ArrayList<ResearchProject> getSpecifiedValidatedAndNotValidatedResearchProjectList(String researchName) throws DataRetrievalException;
>>>>>>> b76b93d15655b9f5dfd27c9fc867dc5e2c09b660
    public ArrayList<ResearchProject> getResearchProjectList() throws DataRetrievalException;
    public int modifyResearch(ResearchProject research) throws DataInsertionException;
    public boolean assertResearch(ResearchProject research);
    public void validateResearch(ResearchProject researchProject) throws DataWritingException;
}
