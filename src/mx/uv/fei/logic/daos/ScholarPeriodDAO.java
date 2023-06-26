package mx.uv.fei.logic.daos;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IScholarPeriodDAO;
import mx.uv.fei.logic.domain.ScholarPeriod;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ScholarPeriodDAO implements IScholarPeriodDAO{
    private final DataBaseManager dataBaseManager;

    public ScholarPeriodDAO(){
        dataBaseManager = new DataBaseManager();
    }

    @Override
    public int addScholarPeriod(ScholarPeriod scholarPeriod) throws DataInsertionException {
        int generatedId = 0;
        String query = 
        "INSERT INTO PeriodosEscolares (fechaInicio, fechaFin) VALUES (?, ?)";
        try{
            PreparedStatement preparedStatement = 
                dataBaseManager.getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, scholarPeriod.getStartDate());
            preparedStatement.setDate(2, scholarPeriod.getEndDate());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if(resultSet.next()){
                generatedId = resultSet.getInt(1);
            }

        }catch(SQLException e){
            throw new DataInsertionException("Error al agregar periodo escolar. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }
        return generatedId;
    }

    @Override
    public int modifyScholarPeriod(ScholarPeriod scholarPeriod) throws DataInsertionException {
        int result = 0;
        try{
            String query = "UPDATE PeriodosEscolares SET fechaInicio = ?, fechaFin = ? WHERE IdPeriodoEscolar = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, scholarPeriod.getStartDate());
            preparedStatement.setDate(2, scholarPeriod.getEndDate());
            preparedStatement.setInt(3, scholarPeriod.getScholarPeriodId());
            
            result = preparedStatement.executeUpdate();
        }catch(SQLException e){
            throw new DataInsertionException("Error al modificar curso. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return result;
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
    public ArrayList<ScholarPeriod> getSpecifiedScholarPeriodsByStartDate(String startDate) throws DataRetrievalException {
        ArrayList<ScholarPeriod> scholarPeriods = new ArrayList<>();

        try{
            String query = "SELECT * FROM PeriodosEscolares WHERE fechaInicio LIKE ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setString(1, startDate + '%');
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next()) {
                ScholarPeriod scholarPeriod = new ScholarPeriod();
                scholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                scholarPeriod.setStartDate(resultSet.getDate("fechaInicio"));
                scholarPeriod.setEndDate(resultSet.getDate("fechaFin"));
                scholarPeriods.add(scholarPeriod);
            }
            
            resultSet.close();
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

    @Override
    public ScholarPeriod getScholarPeriodByObject(ScholarPeriod scholarPeriod) throws DataRetrievalException {
        ScholarPeriod searchedScholarPeriod = new ScholarPeriod();
        try{
            String query = "SELECT * FROM PeriodosEscolares WHERE fechaInicio = ? && fechaFin = ?";
            PreparedStatement preparedStatement = dataBaseManager.getConnection().prepareStatement(query);
            preparedStatement.setDate(1, scholarPeriod.getStartDate());
            preparedStatement.setDate(2, scholarPeriod.getEndDate());
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next()){
                searchedScholarPeriod.setScholarPeriodId(resultSet.getInt("IdPeriodoEscolar"));
                searchedScholarPeriod.setStartDate(resultSet.getDate("fechaInicio"));
                searchedScholarPeriod.setEndDate(resultSet.getDate("fechaFin"));
            }
            
            resultSet.close();
            dataBaseManager.closeConnection();
        }catch(SQLException e){
            throw new DataRetrievalException("Fallo al recuperar la informacion. Inténtelo de nuevo más tarde");
        }finally{
            dataBaseManager.closeConnection();
        }

        return searchedScholarPeriod;
    }
}
