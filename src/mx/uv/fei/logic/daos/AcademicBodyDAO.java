package mx.uv.fei.logic.daos;

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
    private final DataBaseManager dataBaseManager;
    
    public AcademicBodyDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addAcademicBody(AcademicBody academicBody) throws DataInsertionException {
        int result;
        String query = "INSERT INTO CuerposAcademicos(IdResponsableCA, descripcion) VALUES(?, ?)";
        
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            statement.setInt(1, academicBody.getAcademicBodyHeadID());
            statement.setString(2, academicBody.getDescription());
            
            result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataInsertionException("New Academic Body data could not be saved to the Database. Please try again later");
        }finally{
            dataBaseManager.closeConnection();
        }
        return result;
    }

    @Override
    public ArrayList<AcademicBody> getAcademicBodiesList() throws DataRetrievalException {
        ArrayList<AcademicBody> academicBodiesList = new ArrayList();
        
        String query = "SELECT * FROM CuerposAcademicos";
        try {
            PreparedStatement statement = dataBaseManager.getConnection().prepareStatement(query);
            
            ResultSet rs = statement.executeQuery();
            while(rs.next()) {
                AcademicBody academicBody = new AcademicBody();
                academicBody.setAcademicBodyID(rs.getInt("IdCuerpoAcademico"));
                academicBody.setAcademicBodyHeadID(rs.getInt("IdResponsableCA"));
                academicBody.setDescription(rs.getString("descripcion"));
                
                academicBodiesList.add(academicBody);
            }
        } catch (SQLException exception) {
            throw new DataRetrievalException("Failed to retrieve Academic Bodies information. Please try again later");
        }finally{
            dataBaseManager.closeConnection();
        }
        
        return academicBodiesList;
    }

    @Override
    public AcademicBody getAcademicBodyByID(int academicBodyID) throws DataRetrievalException {     
        ArrayList<AcademicBody> academicBodiesList = new ArrayList(getAcademicBodiesList());
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
