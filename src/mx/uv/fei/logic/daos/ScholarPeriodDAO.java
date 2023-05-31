package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IScholarPeriodDAO;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ScholarPeriodDAO implements IScholarPeriodDAO{
    private final DataBaseManager dataBaseManager;

    public ScholarPeriodDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public ArrayList<ScholarPeriod> getScholarPeriods() throws DataRetrievalException{
        ArrayList<ScholarPeriod> scholarPeriods = new ArrayList<>();

        try{
            Statement statement = dataBaseManager.getConnection().createStatement();
            String query = "SELECT * FROM PeriodosEscolares";
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setStartDate(resultSet.getString("fechaInicio"));
                scholarPeriod.setEndDate(resultSet.getString("fechaFin"));
                scholarPeriods.add(scholarPeriod);
            }
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return scholarPeriods;
    }

    @Override
    public ScholarPeriod getScholarPeriod(int idScholarPeriod) throws DataRetrievalException{
        ScholarPeriod scholarPeriod = new ScholarPeriod();

        try{
            String query = "SELECT * FROM PeriodosEscolares WHERE IdPeriodoEscolar = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, idScholarPeriod);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                scholarPeriod.setIdScholarPeriod(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setStartDate(resultSet.getString("fechaInicio"));
                scholarPeriod.setEndDate(resultSet.getString("fechaFin"));
            }
            
            resultSet.close();
            dataBaseManager.getConnection().close();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Verifique su conexion e intentelo de nuevo");
        }finally{
            dataBaseManager.closeConnection();
        }

        return scholarPeriod;
    }
    
}
