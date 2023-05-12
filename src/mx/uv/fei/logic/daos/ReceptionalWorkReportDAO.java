package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IReceptionalWorkReportDAO;
import mx.uv.fei.logic.domain.Research;

public class ReceptionalWorkReportDAO implements IReceptionalWorkReportDAO{

    @Override
    public ArrayList<Research> getResearchsFromDatabase(String title) {
        ArrayList<Research> researches = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM Anteproyectos WHERE título LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
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
    //YOP
}
