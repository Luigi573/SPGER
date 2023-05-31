package mx.uv.fei.logic.daos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.daosinterfaces.IAcademicBodyDAO;
import mx.uv.fei.logic.domain.AcademicBody;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class AcademicBodyDAO implements IAcademicBodyDAO{
    private DataBaseManager dataBaseManager;
    
    public AcademicBodyDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addAcademicBody(AcademicBody academicBody) throws DataInsertionException {
        int result;
        String query = "insert into CuerposAcademicos(IdResponsableCA, descripcion) values(?, ?)";
        try {
            Connection connection = dataBaseManager.getConnection();
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, academicBody.getAcademicBodyHeadID());
            statement.setString(2, academicBody.getDescription());
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("New Academic Body data could not be saved to the Database. Please try again later");
        }
        return result;
    }

    @Override
    public ArrayList<AcademicBody> getAcademicBodiesList() throws DataRetrievalException {
        ArrayList<AcademicBody> academicBodiesList = new ArrayList<>();
        
        String query = "select * from CuerposAcademicos";
        try {
            DataBaseManager dataBaseManager = new DataBaseManager();
            Connection connection = dataBaseManager.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);
            while(rs.next()) {
                AcademicBody academicBody = new AcademicBody();
                academicBody.setAcademicBodyID(rs.getInt("IdCuerpoAcademico"));
                academicBody.setAcademicBodyHeadID(rs.getInt("IdResponsableCA"));
                academicBody.setDescription(rs.getString("descripcion"));
                
                academicBodiesList.add(academicBody);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve Academic Bodies information. Please try again later");
        }
        
        return academicBodiesList;
    }

    @Override
    public AcademicBody getAcademicBodyByID(int academicBodyID) throws DataRetrievalException {     
        ArrayList<AcademicBody> academicBodiesList = new ArrayList<>(getAcademicBodiesList());
        AcademicBody academicBodyByID = new AcademicBody();
        int i = 0;
        boolean b = false;
        
        while(i < academicBodiesList.size() && !b) {
            if(academicBodiesList.get(i).getAcademicBodyID() == academicBodyID) {
                academicBodyByID = academicBodiesList.get(i);
                b = true;
            }
            i++;
        }

        if(!b){
            System.out.println("There is no Academic Body that matches the given ID.");
        }
        
        return academicBodyByID;
    }
    
}
