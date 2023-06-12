package mx.uv.fei.logic.daos.exceptions;

import java.util.ArrayList;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import org.junit.Test;

public class ResearchDAOExceptionsTest {
    
    @Test(expected = DataInsertionException.class)
    public void testAddResearchException() throws DataInsertionException {
        ResearchProject research = new ResearchProject();
        ResearchDAO researchDAO = new ResearchDAO();
        
        researchDAO.addResearch(research);
    }
    
    @Test(expected = DataRetrievalException.class)
    public void testGetResearchProjectListException() throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList();
        ResearchDAO researchDAO = new ResearchDAO();
        
        researchList = researchDAO.getResearchProjectList();
    }
    
    @Test(expected = DataRetrievalException.class)
    public void testGetDirectorsResearchException() throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList();
        ResearchDAO researchDAO = new ResearchDAO();
        
        
    }

    @Test(expected = DataRetrievalException.class)
    public void testGetStudentsResearchException() throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList();
        ResearchDAO researchDAO = new ResearchDAO();
        
        
    }

    @Test(expected = DataRetrievalException.class)
    public void testGetCourseResearchException() throws DataRetrievalException{
        ArrayList<ResearchProject> researchList = new ArrayList();
        ResearchDAO researchDAO = new ResearchDAO();
        
        
    }
    
    @Test(expected = DataInsertionException.class)
    public void testModifyResearchException() throws DataInsertionException{
        ArrayList<ResearchProject> researchList = new ArrayList();
        ResearchDAO researchDAO = new ResearchDAO();
        
        
    }
}