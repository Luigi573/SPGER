package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public interface IResearchProjectDAO {
    public int addResearchProject(ResearchProject researchProject) throws DataInsertionException;

    public ArrayList<ResearchProject> getResearchProjectsList() throws DataRetrievalException;

    public ArrayList<ResearchProject> getDirectorsResearch(int staffNumber) throws DataRetrievalException;

    public int modifyResearchProject(ResearchProject researchProject) throws DataInsertionException;

    public boolean assertResearchProject(ResearchProject researchProject);

    public void validateResearchProject(ResearchProject researchProject) throws DataInsertionException;
}
