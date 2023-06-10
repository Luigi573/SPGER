package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchesReportDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchReportDAO implements IResearchesReportDAO{
    private final DataBaseManager dataBaseManager;

    public ResearchReportDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public ArrayList<ResearchProject> getResearches(String title, String query) throws DataRetrievalException{
        ArrayList<ResearchProject> researches = new ArrayList<>();

        try{
            if(query == ""){
                query = "SELECT * FROM Anteproyectos A LEFT JOIN Directores D ON A.IdUsuario = D.IdUsuario "+
                        "LEFT JOIN Estudiantes E ON A.IdUsuario = E.IdUsuario LEFT JOIN LGAC L ON A.IdKgal = "+
                        "K.IdLGAC WHERE A.título LIKE ?";
            }
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                research.setTitle(resultSet.getString("título"));
                research.setDirector(resultSet.getString("codirector"));
                research.setRequirements(resultSet.getString("requisitos"));
                research.setDescription(resultSet.getString("descripción"));
                research.setValidationStatus(resultSet.getString("V°B°"));
                research.setSuggestedBibliography(resultSet.getString("bibliografíaRecomendada"));
                research.setStartDate(resultSet.getDate("fechaInicio"));
                research.setDueDate(resultSet.getDate("fechaFin"));
                research.setExpectedResult(resultSet.getString("resultadosEsperados"));
                research.setNote(resultSet.getString("nota"));
                researches.add(research);
            }
            resultSet.close();
            dataBaseManager.closeConnection();

        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return researches;
    }

    @Override
    public ArrayList<ResearchProject> getValidatedResearches(String title) throws DataRetrievalException{
        ArrayList<ResearchProject> validatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && V°B° = 'Validado'";
        validatedResearches = getResearches(title, query);
        return validatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getNotValidatedResearches(String title) throws DataRetrievalException{
        ArrayList<ResearchProject> notValidatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && V°B° = 'No Validado'";
        notValidatedResearches = getResearches(title, query);
        return notValidatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getValidatedAndNotValidatedResearches(String title) throws DataRetrievalException{
        ArrayList<ResearchProject> validatedAndNotValidatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && (V°B° = 'Validado' || V°B° = 'No Validado')";
        validatedAndNotValidatedResearches = getResearches(title, query);
        return validatedAndNotValidatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getSelectedResearches(ArrayList<String> selectedResearchesTitles) throws DataRetrievalException{
        DataBaseManager dataBaseManager = new DataBaseManager();
        ArrayList<ResearchProject> selectedResearches = new ArrayList<>();

        String fullQuery = "SELECT * FROM Anteproyectos WHERE título = ";
        for(int i = 1; i <= selectedResearchesTitles.size(); i++){
            fullQuery = fullQuery + "?";
            if(i < selectedResearchesTitles.size()){
                fullQuery = fullQuery + " || título = ";
            }
        }

        try{
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(fullQuery);
            int researchesListForQueryCounter = 1;
            for(String research : selectedResearchesTitles){
                preparedStatement.setString(researchesListForQueryCounter, research);
                researchesListForQueryCounter++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                research.setTitle(resultSet.getString("título"));
                research.setCodirector(resultSet.getString("codirector"));
                research.setRequeriments(resultSet.getString("requisitos"));
                research.setDescription(resultSet.getString("descripción"));
                research.setVoBo(resultSet.getString("V°B°"));
                research.setRecomendedBibliography(resultSet.getString("bibliografíaRecomendada"));
                research.setStartDate(resultSet.getString("fechaInicio"));
                research.setFinishDate(resultSet.getString("fechaFin"));
                research.setWaitedResults(resultSet.getString("resultadosEsperados"));
                research.setNote(resultSet.getString("nota"));
                selectedResearches.add(research);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return selectedResearches;
    }
    
}
