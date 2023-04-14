package mx.uv.fei;

import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.domain.*;
import mx.uv.fei.logic.daos.*;


public class Main {
    public static void main (String arg[]) throws SQLException {
        DataBaseManager dbm = new DataBaseManager();
        dbm.getConnection();
        
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        
//        AcademicBody academicBody1 = new AcademicBody(1, "Testing academic body Data Access Object");
//        AcademicBody academicBody2 = new AcademicBody(2, "Ingenieria de Software");
//        AcademicBody academicBody3 = new AcademicBody(3, "Redes");
//        
//        academicBodyDAO.addAcademicBody(academicBody1);
//        academicBodyDAO.addAcademicBody(academicBody2);
//        academicBodyDAO.addAcademicBody(academicBody2);
        //Academic Body info successfully added to . addAcademicBody() works fine
        
//        try {
//            ArrayList<AcademicBody> academicBodiesList = new ArrayList(academicBodyDAO.getAcademicBodyList());
//            for(int i = 0; i < 4; i++) {
//                System.out.println(academicBodiesList.get(i).getAcademicBodyHeadID());
//                System.out.println(academicBodiesList.get(i).getDescription() + "\n");
//            }
//        } catch (DataRetrievalException dre) {
//            System.out.println(dre.getMessage());
//        }
        //Academic Bodies successfully retrieved from DB. getAcademicBodyList() works fine
        
//        try {
//            System.out.println(academicBodyDAO.getAcademicBodyByID(1).getDescription());
//        } catch (DataRetrievalException dre) {
//            System.out.println(dre.getMessage());
//        }
        //getAcademicBodyByID works, but in a weird way so it could probably use some improvements
    }
}
