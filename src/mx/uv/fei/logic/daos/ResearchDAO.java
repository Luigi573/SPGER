package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IResearchDAO;
import mx.uv.fei.logic.domain.Research;

public class ResearchDAO implements IResearchDAO{

    @Override
    public ArrayList<Research> getResearchFromDatabase() {
        ArrayList<Research> researches = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM Anteproyectos";
            ResultSet resultSet = statement.executeQuery(query);
            if(resultSet.next()){
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
    public void setResearchToDatabase(Research research) {
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String tablesToConsult = 
            "título, codirector, requisitos, descripción, V°B°, bibliografíaRecomendada, fechaInicio, fechaFin, resultadosEsperados, nota";
            String wholeQuery = "INSERT INTO Anteproyectos (" + tablesToConsult + ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(wholeQuery);
            preparedStatement.setString(1, research.getTitle());
            preparedStatement.setString(2, research.getCodirector());
            preparedStatement.setString(3, research.getRequeriments());
            preparedStatement.setString(4, research.getDescription());
            preparedStatement.setString(5, research.getVoBo());
            preparedStatement.setString(6, research.getRecomendedBibliography());
            preparedStatement.setString(7, research.getStartDate());
            preparedStatement.setString(8, research.getFinishDate());
            preparedStatement.setString(9, research.getWaitedResults());
            preparedStatement.setString(10, research.getNote());
            preparedStatement.executeUpdate();
            preparedStatement.close();
            dataBaseManager.getConnection().close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return result;
    }
    @Override
    public boolean assertResearch(ResearchProject research) {
        return !isBlank(research) && isValidDate(research);
    }
    public boolean isBlank(ResearchProject research){
        return research.getTitle().isBlank();
    }
    public boolean isValidDate(ResearchProject research){
        return research.getStartDate().compareTo(research.getDueDate()) <= 0;
    }

    @Override
    public boolean assertResearch(ResearchProject research){
        return !isBlank(research) && isValidDate(research);
    }

    public boolean isBlank(ResearchProject research){
        return research.getTitle().isBlank();
    }

    public boolean isValidDate(ResearchProject research){
        return research.getStartDate().compareTo(research.getDueDate()) <= 0;
    }

}
