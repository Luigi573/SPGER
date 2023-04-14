package mx.uv.fei;

import java.sql.SQLException;
import java.util.ArrayList;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.domain.*;
import mx.uv.fei.logic.daos.AcademicBodyDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.ReceptionalWorkDAO;


public class Main {
    public static void main (String arg[]) {
        
        AcademicBody academicBody1 = new AcademicBody(1, "Testing academic body Data Access Object");
        AcademicBody academicBody2 = new AcademicBody(2, "Ingenieria de Software");
        AcademicBody academicBody3 = new AcademicBody(3, "Redes"); 
              
        Advance advance1 = new Advance("zs21013873", 1, "Avance de Prueba No. 1", "Este avance es de prueba");
        Advance advance2 = new Advance("zs21013873", 1, "Resumen capitulo 4 del libro tal", "Ya leí el capítulo 4 e hice un resumen con la información más relevante");
        Advance advance3 = new Advance("zs21013873", 1, "Resumen capitulo 5 del libro tal", "Para este capítulo realicé tambié un cuadro sinóptico");
        
        ReceptionalWork receptionalWork = new ReceptionalWork(1, "Trabajo Recepcional número 1", "Este es un trabajo recepcional de prueba");

        AcademicBody academicBody = new AcademicBody (5, "Tecnologías computacionales");
        testAddAcademicBody(academicBody);
        testAddAcademicBody(academicBody1);
        testAddAcademicBody(academicBody2);
        testAddAcademicBody(academicBody3);
        testGetAcademicBodiesList();
        testGetAcademicBodyByID(5);
        
        Advance advance = new Advance("zs21013873", 1, "Tabla comparativa de los métodos de tal", "Me retrasé porque tuve que salir de la ciudad. Tengo dudas en una parte...");
        testAddAdvance(advance);
        testAddAdvance(advance1);
        testAddAdvance(advance2);
        testAddAdvance(advance3);
        testGetAdvancesList();
        testGetAdvanceByID(10);

        ReceptionalWork receptionalWork2 = new ReceptionalWork(2, "Trabajo Recepcional número 2", "Este también es un trabajo recepcional de prueba");
        testAddReceptionalWork(receptionalWork1);
        testAddReceptionalWork(receptionalWork2);
        testGetReceptionalWorksList();
        testGetReceptionalWorkByID(1);
    }
    
    //tested
    public static void testAddAcademicBody(AcademicBody academicBody) {
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        try {
            academicBodyDAO.addAcademicBody(academicBody);
        } catch (DataInsertionException die) {
            System.out.println(die.getMessage());
        }
    }
    
    //tested
    public static void testGetAcademicBodiesList() {
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        try {
            ArrayList<AcademicBody> academicBodiesList = new ArrayList(academicBodyDAO.getAcademicBodiesList());
            for(int i = 0; i < academicBodiesList.size(); i++) {
                System.out.println(academicBodiesList.get(i).getAcademicBodyHeadID());
                System.out.println(academicBodiesList.get(i).getDescription() + "\n");
            }
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
    
    //tested
    public static void testGetAcademicBodyByID(int id) {
        AcademicBodyDAO academicBodyDAO = new AcademicBodyDAO();
        try {
            System.out.println(academicBodyDAO.getAcademicBodyByID(id).getDescription());
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
    
    //tested
    public static void testAddAdvance(Advance advance) {
        AdvanceDAO advanceDAO = new AdvanceDAO();
        try {
            advanceDAO.addAdvance(advance);
        } catch (DataInsertionException die) {
            System.out.println(die.getMessage());
        }
    }
    
    //tested
    public static void testGetAdvancesList() {
        AdvanceDAO advanceDAO = new AdvanceDAO();
        try {
            ArrayList<Advance> advancesList = new ArrayList(advanceDAO.getAdvancesList());
            for(int i = 0; i < advancesList.size(); i++) {
                System.out.println(advancesList.get(i).getAdvanceID());
                System.out.println(advancesList.get(i).getTitle() + "\n");
            }
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
    
    //tested
    public static void testGetAdvanceByID(int id) {
        AdvanceDAO advanceDAO = new AdvanceDAO();
        try {
            System.out.println(advanceDAO.getAdvanceByID(id).getTitle());
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
    
    //tested
    public static void testAddReceptionalWork(ReceptionalWork receptionalWork) {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        try {
            receptionalWorkDAO.addReceptionalWork(receptionalWork);
        } catch (DataInsertionException die) {
            System.out.println(die.getMessage());
        }
    }
    
    //tested
    public static void testGetReceptionalWorksList() {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        try {
            ArrayList<ReceptionalWork> receptionalWorksList = new ArrayList(receptionalWorkDAO.getReceptionalWorksList());
            for(int i = 0; i < receptionalWorksList.size(); i++) {
                System.out.println(receptionalWorksList.get(i).getReceptionalWorkID());
                System.out.println(receptionalWorksList.get(i).getDescription() + "\n");
            }
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
    
    //tested
    public static void testGetReceptionalWorkByID(int id) {
        ReceptionalWorkDAO receptionalWorkDAO = new ReceptionalWorkDAO();
        try {
            System.out.println(receptionalWorkDAO.getReceptionalWorkByID(id).getDescription());
        } catch (DataRetrievalException dre) {
            System.out.println(dre.getMessage());
        }
    }
}
