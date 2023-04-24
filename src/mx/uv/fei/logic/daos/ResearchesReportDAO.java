package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchesReportDAO;
import mx.uv.fei.logic.domain.Research;

public class ResearchesReportDAO implements IResearchesReportDAO{

    @Override
    public ArrayList<Research> getResearchesFromDatabase(String title, String query) {
        ArrayList<Research> researches = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            if(query == ""){
                query = "SELECT * FROM Anteproyectos WHERE título LIKE ?";
            }
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Research research = new Research();
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
                researches.add(research);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return researches;
    }

    @Override
    public ArrayList<Research> getValidatedResearchesFromDatabase(String title) {
        ArrayList<Research> validatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && V°B° = 'Validado'";
        validatedResearches = this.getResearchesFromDatabase(title, query);
        return validatedResearches;
    }

    @Override
    public ArrayList<Research> getNotValidatedResearchesFromDatabase(String title) {
        ArrayList<Research> notValidatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && V°B° = 'No Validado'";
        notValidatedResearches = this.getResearchesFromDatabase(title, query);
        return notValidatedResearches;
    }

    @Override
    public ArrayList<Research> getValidatedAndNotValidatedResearchesFromDatabase(String title) {
        ArrayList<Research> validatedAndNotValidatedResearches;
        String query = "SELECT * FROM Anteproyectos WHERE título LIKE ? && (V°B° = 'Validado' || V°B° = 'No Validado')";
        validatedAndNotValidatedResearches = this.getResearchesFromDatabase(title, query);
        return validatedAndNotValidatedResearches;
    }

    @Override
    public ArrayList<Research> getSelectedResearchesFromDatabase(ArrayList<String> selectedResearchesTitles) {
        DataBaseManager dataBaseManager = new DataBaseManager();
        ArrayList<Research> selectedResearches = new ArrayList<>();

        String fullQuery = "SELECT * FROM Anteproyectos WHERE título = ";
        for(int i = 1; i <= selectedResearchesTitles.size(); i++){
            fullQuery = fullQuery + "?";
            if(i < selectedResearchesTitles.size()){
                fullQuery = fullQuery + " || título = ";
            }
        }

        try {
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(fullQuery);
            int researchesListForQueryCounter = 1;
            for(String research : selectedResearchesTitles){
                preparedStatement.setString(researchesListForQueryCounter, research);
                researchesListForQueryCounter++;
            }

            ResultSet resultSet = preparedStatement.executeQuery();
            while(resultSet.next()){
                Research research = new Research();
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
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return selectedResearches;
    }
    
}
