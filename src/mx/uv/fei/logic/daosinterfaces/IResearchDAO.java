package mx.uv.fei.logic.daosinterfaces;

import java.util.ArrayList;

import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public interface IResearchDAO {
    public int addResearch(ResearchProject research) throws DataInsertionException;
    public ArrayList<ResearchProject> getResearchProjectList() throws DataRetrievalException;
    public int modifyResearch(ResearchProject research) throws DataInsertionException;
    public boolean assertResearch(ResearchProject research);
}
