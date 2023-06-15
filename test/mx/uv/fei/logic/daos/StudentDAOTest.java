package mx.uv.fei.logic.daos;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.statuses.StudentStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.exceptions.DuplicatedPrimaryKeyException;

public class StudentDAOTest {
    private static DataBaseManager dataBaseManager;
    private static Student preloadedStudent;
    private static Student failedStudent;
    private static Student preloadedStudentForAddStudentTest;

    @BeforeClass
    public static void setUpClass() {
        dataBaseManager = new DataBaseManager();
        
        try{
            //Adding a Student
            preloadedStudent = new Student();
            preloadedStudent.setName("Abraham David");
            preloadedStudent.setFirstSurname("Vazquez");
            preloadedStudent.setSecondSurname("Quito");
            preloadedStudent.setEmailAddress("abrvaqu862@gmail.com");
            preloadedStudent.setAlternateEmail("abrvaqu999@gmail.com");
            preloadedStudent.setPhoneNumber("2289487337");
            preloadedStudent.setStatus(StudentStatus.ACTIVE.getValue());
            preloadedStudent.setMatricle("zS34829301");

            String userQuery = "INSERT INTO Usuarios(nombre, apellidoPaterno, apellidoMaterno, correo, correoAlterno, numeroTelefono, estado) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement userStatement = dataBaseManager.getConnection().prepareStatement(userQuery, Statement.RETURN_GENERATED_KEYS);
            userStatement.setString(1, preloadedStudent.getName());
            userStatement.setString(2, preloadedStudent.getFirstSurname());
            userStatement.setString(3, preloadedStudent.getSecondSurname());
            userStatement.setString(4, preloadedStudent.getEmailAddress());
            userStatement.setString(5, preloadedStudent.getAlternateEmail());
            userStatement.setString(6, preloadedStudent.getPhoneNumber());
            userStatement.setString(7, preloadedStudent.getStatus());
            userStatement.executeUpdate();
            
            ResultSet generatedUserKeys = userStatement.getGeneratedKeys();
            if(generatedUserKeys.next()){
                preloadedStudent.setUserId(generatedUserKeys.getInt(1));
            }
            generatedUserKeys.close();
            userStatement.close();
            
            String professorQuery = "INSERT INTO Estudiantes(Matrícula, IdUsuario) VALUES (?,?)";
            PreparedStatement professorStatement = dataBaseManager.getConnection().prepareStatement(professorQuery);
            professorStatement.setString(1, preloadedStudent.getMatricle());
            professorStatement.setInt(2, preloadedStudent.getUserId());
            professorStatement.executeUpdate();
            professorStatement.close();
        
            //Set data to a failed professor
            failedStudent = new Student();
            failedStudent.setName("Luis Roberto");
            failedStudent.setFirstSurname("Justo");
            failedStudent.setSecondSurname("Moreno");
            failedStudent.setEmailAddress("lurojumo892@gmail.com");
            failedStudent.setAlternateEmail("lurojumo783@gmail.com");
            failedStudent.setPhoneNumber("2283788222");
            failedStudent.setStatus(StudentStatus.DROPPED.getValue());
            failedStudent.setMatricle("zS38194728");

            //Set data to a Student to use in addStudentTest
            preloadedStudentForAddStudentTest = new Student();
            preloadedStudentForAddStudentTest.setName("Mauricio");
            preloadedStudentForAddStudentTest.setFirstSurname("Ortega");
            preloadedStudentForAddStudentTest.setSecondSurname("Mújica");
            preloadedStudentForAddStudentTest.setEmailAddress("maormu12892@gmail.com");
            preloadedStudentForAddStudentTest.setMatricle("zS27827645");
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
    
    @AfterClass
    public static void tearDownClass(){
        PreparedStatement statement;
        String queryToDeleteUser = "DELETE FROM Usuarios WHERE IdUsuario = ? || IdUsuario = ?";
        String queryToDeleteProfessor = "DELETE FROM Estudiantes WHERE Matrícula = ? || Matrícula = ?";
        
        try{
            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteUser);
            statement.setInt(1, preloadedStudent.getUserId());
            statement.setInt(2, preloadedStudentForAddStudentTest.getUserId());
            statement.executeUpdate();

            statement = dataBaseManager.getConnection().prepareStatement(queryToDeleteProfessor);
            statement.setString(1, preloadedStudent.getMatricle());
            statement.setString(2, preloadedStudentForAddStudentTest.getMatricle());
            statement.executeUpdate();
            statement.close();
        }catch(SQLException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void addStudentTest() {
        try {
            StudentDAO instance = new StudentDAO();
            int result = instance.addStudent(preloadedStudentForAddStudentTest);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }catch(DuplicatedPrimaryKeyException e) {
            fail("Duplicated primary key");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentsTest() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getStudents();
            assertTrue(result.contains(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentsTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getStudents();
            assertTrue(!result.contains(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentListTest() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getStudentList();
            assertTrue(result.contains(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentListTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getStudentList();
            assertTrue(!result.contains(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getActiveStudentsTest() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getActiveStudents();
            assertTrue(result.contains(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getActiveStudentsTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getActiveStudents();
            assertTrue(!result.contains(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedActiveStudentsTest() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getSpecifiedActiveStudents("A");
            assertTrue(result.contains(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedActiveStudentsTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getSpecifiedActiveStudents("A");
            assertTrue(!result.contains(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedStudentsTest() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getSpecifiedStudents("K");
            assertTrue(result.contains(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getSpecifiedStudentsTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            ArrayList<Student> result = instance.getSpecifiedStudents("A");
            assertTrue(!result.contains(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentTest() {
        try {
            StudentDAO instance = new StudentDAO();
            Student result = instance.getStudent(preloadedStudent.getMatricle());
            assertTrue(result.equals(preloadedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void getStudentTestFail() {
        try {
            StudentDAO instance = new StudentDAO();
            Student result = instance.getStudent(preloadedStudent.getMatricle());
            assertTrue(!result.equals(failedStudent));
        }catch(DataRetrievalException exception){
            fail("Couldn't connect to DB");
        }finally{
            dataBaseManager.closeConnection();
        }
    }

    @Test
    public void modifyStudentDataTest(){
        try {
            StudentDAO instance = new StudentDAO();
            preloadedStudent.setName("Katerine");
            preloadedStudent.setFirstSurname("O'");
            preloadedStudent.setSecondSurname("Hara");
            preloadedStudent.setEmailAddress("katoje934@outlook.com");
            int result = instance.modifyStudentData(preloadedStudent);
            assertTrue(result > 0);
        }catch(DataInsertionException exception){
            fail("Couldn't connect to DB");
        }catch(DuplicatedPrimaryKeyException e) {
            fail("Duplicated primary key");
        }finally{
            dataBaseManager.closeConnection();
        }
    }
}
