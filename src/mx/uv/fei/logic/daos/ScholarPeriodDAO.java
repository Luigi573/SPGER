package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        PreparedStatement statement;
        String query = "SELECT * FROM PeriodosEscolares";

        try{
            statement = dataBaseManager.getConnection().prepareStatement(query);
            
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) {
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setStartDate(resultSet.getDate("fechaInicio"));
                scholarPeriod.setEndDate(resultSet.getDate("fechaFin"));
                scholarPeriods.add(scholarPeriod);
            }
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return scholarPeriods;
    }

    @Override
    public ScholarPeriod getScholarPeriod(int scholarPeriodId) throws DataRetrievalException{
        ScholarPeriod scholarPeriod = new ScholarPeriod();

        try{
            String query = "SELECT * FROM PeriodosEscolares WHERE IdPeriodoEscolar = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setInt(1, scholarPeriodId);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setStartDate(resultSet.getDate("fechaInicio"));
                scholarPeriod.setEndDate(resultSet.getDate("fechaFin"));
            }
            
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return scholarPeriod;
    }
    
}
