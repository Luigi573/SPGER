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

public class AcademicBodyDAO implements IAcademicBodyDAO{
    private DataBaseManager dataBaseManager;
    
    public AcademicBodyDAO() {
        dataBaseManager = new DataBaseManager();
    }
    
    @Override
    public int addAcademicBody(AcademicBody academicBody) throws SQLException {
        int result;
        String query = "insert into CuerposAcademicos(IdResponsableCA, descripcion) values(?, ?)";
        DataBaseManager dataBaseManager = new DataBaseManager();
        Connection connection = dataBaseManager.getConnection();
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1, academicBody.getAcademicBodyHeadID());
        statement.setString(2, academicBody.getDescription());
        result = statement.executeUpdate();
        return result;
    }

    @Override
    public ArrayList<AcademicBody> getAcademicBodyList() throws DataRetrievalException {
        ArrayList<AcademicBody> academicBodies = new ArrayList();
        
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
                
                academicBodies.add(academicBody);
            }
        } catch (SQLException sql) {
            throw new DataRetrievalException("Failed to retrieve Academic Bodies information. Please try again later");
        }
        
        return academicBodies;
    }

    @Override
    public AcademicBody getAcademicBodyByID(int academicBodyID) throws DataRetrievalException {     
        ArrayList<AcademicBody> academicBodiesList = new ArrayList(getAcademicBodyList());
        AcademicBody academicBodyByID = new AcademicBody();
        int i = 0;
        boolean b = false;
        
        while(i < academicBodiesList.size() && !b) {
            if(academicBodiesList.get(i).getAcademicBodyID() == academicBodyID) {
                academicBodyByID = academicBodiesList.get(i);
                System.out.println("We found a match");
                b = true;
            } else {
                System.out.println("No match");
            }
        }
        
        return academicBodyByID;
    }
    
}
