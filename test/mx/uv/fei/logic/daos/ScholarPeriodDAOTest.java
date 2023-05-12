package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IScholarPeriodDAO;
import mx.uv.fei.logic.domain.ScholarPeriod;

public class ScholarPeriodDAOTest implements IScholarPeriodDAO{

    @Override
    public ArrayList<ScholarPeriod> getScholarPeriodsFromDatabase() {
        ArrayList<ScholarPeriod> scholarPeriods = new ArrayList<>();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM PeriodosEscolares";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setEndDate(resultSet.getString("fechaInicio"));
                scholarPeriod.setStartDate(resultSet.getString("fechaFin"));
                scholarPeriods.add(scholarPeriod);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scholarPeriods;
    }

    @Override
    public ScholarPeriod getScholarPeriodFromDatabase(int idScholarPeriod) {
        ScholarPeriod scholarPeriod = new ScholarPeriod();

        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            String query = "SELECT * FROM PeriodosEscolares WHERE IdPeriodoEscolar = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idScholarPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                scholarPeriod.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setEndDate(resultSet.getString("fechaInicio"));
                scholarPeriod.setStartDate(resultSet.getString("fechaFin"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return scholarPeriod;
    }
    
}
