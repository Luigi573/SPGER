package mx.uv.fei;

import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.domain.*;
import mx.uv.fei.logic.daos.AcademicBodyDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;


public class Main {
    public static void main (String arg[]) throws SQLException {
        DataBaseManager dbm = new DataBaseManager();
        dbm.getConnection();
        
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        
//        AcademicBody academicBody1 = new AcademicBody(1, "Testing academic body Data Access Object");
//        AcademicBody academicBody2 = new AcademicBody(2, "Ingenieria de Software");
//        AcademicBody academicBody3 = new AcademicBody(3, "Redes"); 

//        try {
//            academicBodyDAO.addAcademicBody(academicBody1);
//            academicBodyDAO.addAcademicBody(academicBody2);
//            academicBodyDAO.addAcademicBody(academicBody2);
//        } catch (DataInsertionException die) {
//            System.out.println(die.getMessage());
//        }
        //Academic Body info successfully added to DB. addAcademicBody() works fine
        
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
        //getAcademicBodyByID() works, but in a weird way so it could probably use some improvements
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
//        Advance advance1 = new Advance("zs21013873", 1, "Avance de Prueba No. 1", "Este avance es de prueba");
//        Advance advance2 = new Advance("zs21013873", 1, "Resumen capitulo 4 del libro tal", "Ya leí el capítulo 4 e hice un resumen con la información más relevante");
//        Advance advance3 = new Advance("zs21013873", 1, "Resumen capitulo 5 del libro tal", "Para este capítulo realicé tambié un cuadro sinóptico");
//        try {
//            advanceDAO.addAdvance(advance1);
//            advanceDAO.addAdvance(advance2);
//            advanceDAO.addAdvance(advance3);
//        } catch (DataInsertionException die) {
//            System.out.println(die.getMessage());
//        }
        //addAdvance() works properly
  
//        try {
//            ArrayList<Advance> advancesList = new ArrayList(advanceDAO.getAdvancesList());
//            for(int i = 0; i < advancesList.size(); i++) {
//                System.out.println(advancesList.get(i).getAdvanceID());
//                System.out.println(advancesList.get(i).getTitle() + "\n");
//            }
//        } catch (DataRetrievalException dre) {
//            System.out.println(dre.getMessage());
//        }
        //getAdvancesList() works fine
        
        try {
            System.out.println(advanceDAO.getAdvanceByID(3).getTitle());
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
        //getAdvanceByID() works, but same as getAcademicBodyByID()
    }
}
