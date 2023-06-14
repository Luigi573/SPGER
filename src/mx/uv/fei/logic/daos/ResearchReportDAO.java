package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchesReportDAO;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.KGAL;
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
                query = "SELECT título FROM Anteproyectos WHERE título LIKE ?";
            }
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                ResearchProject research = new ResearchProject();
                research.setTitle(resultSet.getString("título"));
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
        String query = "SELECT título FROM Anteproyectos WHERE título LIKE ? && V°B° = 'Validado'";
        validatedResearches = getResearches(title, query);
        return validatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getNotValidatedResearches(String title) throws DataRetrievalException{
        ArrayList<ResearchProject> notValidatedResearches;
        String query = "SELECT título FROM Anteproyectos WHERE título LIKE ? && V°B° = 'No Validado'";
        notValidatedResearches = getResearches(title, query);
        return notValidatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getValidatedAndNotValidatedResearches(String title) throws DataRetrievalException{
        ArrayList<ResearchProject> validatedAndNotValidatedResearches;
        String query = "SELECT título FROM Anteproyectos WHERE título LIKE ? && (V°B° = 'Validado' || V°B° = 'No Validado')";
        validatedAndNotValidatedResearches = getResearches(title, query);
        return validatedAndNotValidatedResearches;
    }

    @Override
    public ArrayList<ResearchProject> getSelectedResearches(ArrayList<String> selectedResearchesTitles) throws DataRetrievalException{
        DataBaseManager dataBaseManager = new DataBaseManager();
        ArrayList<ResearchProject> selectedResearches = new ArrayList<>();

        String fullQuery = "SELECT A.título, U.nombre, U.apellidoPaterno, U.apellidoMaterno, K.descripción, A.V°B°, A.fechaInicio, A.fechafin FROM Anteproyectos A " +
                           "LEFT JOIN Directores D ON A.IdDirector1 = D.IdDirector LEFT JOIN Profesores P ON D.NumPersonal = P.NumPersonal " +
                           "LEFT JOIN Usuarios U ON P.IdUsuario = U.IdUsuario LEFT JOIN LGAC K ON A.IdLGAC = K.IdLGAC WHERE título = ";
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

                if(resultSet.getString("nombre") != null &&
                   resultSet.getString("apellidoPaterno") != null &&
                   resultSet.getString("apellidoMaterno") != null){

                    Director director = new Director();
                    director.setName(resultSet.getString("nombre"));
                    director.setFirstSurname(resultSet.getString("apellidoPaterno"));
                    director.setSecondSurname(resultSet.getString("apellidoMaterno"));
                    research.addDirector(director);
                }
                
                if(resultSet.getString("descripción") != null){
                    KGAL kgal = new KGAL();
                    kgal.setDescription(resultSet.getString("descripción"));
                    research.setKgal(kgal);
                }else{
                    research.setKgal(null);
                }

                research.setValidationStatus(resultSet.getString("V°B°"));
                
                research.setStartDate(resultSet.getDate("fechaInicio"));
                research.setDueDate(resultSet.getDate("fechaFin"));

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
