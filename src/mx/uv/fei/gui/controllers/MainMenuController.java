package mx.uv.fei.gui.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.sql.Date;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.daos.StudentsCoursesDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class MainMenuController{
    private ArrayList<Course> courseList;
    private User user;
        
    @FXML
    private HBox courseHBox;
    @FXML
    private Pane headerPane;
    @FXML
    private VBox courseVBox;
    
    @FXML
    private void initialize() {
        courseList = new ArrayList<>();
    }
    @FXML
    private void exitButtonController(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/Login.fxml"));
            Parent parent = loader.load();            
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();
            Stage oldStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            oldStage.close();
        }catch(IOException exception){            
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setUser(User user){
        this.user = user;
        
        loadCourses();
        loadSpecialPanes();
    }
    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController controller = (HeaderPaneController)loader.getController();
            controller.setUser(user);
            
            headerPane.getChildren().setAll(header);
        }catch(IOException exception){
               
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
        
    private void loadCourses(){
        if(Student.class.isAssignableFrom(user.getClass())){
            StudentsCoursesDAO studentsCoursesDAO = new StudentsCoursesDAO();
            
            try{
                Student student = (Student)user;
                courseList = studentsCoursesDAO.getStudentCourses(student.getMatricle());
                
                for(Course course: courseList){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/CourseHBoxPane.fxml"));

                    try{
                        Pane pane = loader.load();
                        CourseHBoxPaneController controller = (CourseHBoxPaneController)loader.getController();
                        controller.setUser(user);
                        controller.setCourse(course);
                        
                        courseHBox.getChildren().add(pane);
                    }catch(IOException exception){
                        new AlertPopUpGenerator().showMissingFilesMessage();
                    }
                }
            }catch(DataRetrievalException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }else{
            try{
                CourseDAO courseDAO = new CourseDAO();
                courseList = courseDAO.getCourses();
                
                for(Course course: courseList){
                    if(Professor.class.isAssignableFrom(user.getClass()) && course.getProfessor().equals(user) && course.getScholarPeriod().getEndDate().compareTo(Date.valueOf(LocalDate.now())) > 0){
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/CourseHBoxPane.fxml"));
                        
                        try{
                            Pane pane = loader.load();
                            CourseHBoxPaneController controller = (CourseHBoxPaneController)loader.getController();
                            controller.setUser(user);
                            controller.setCourse(course);
                            
                            courseHBox.getChildren().add(pane);
                        }catch(IOException exception){
                            new AlertPopUpGenerator().showMissingFilesMessage();
                        }
                    }
                }
            }catch(DataRetrievalException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }
    }    
    private void loadSpecialPanes(){
        if(DegreeBoss.class.isAssignableFrom(user.getClass())){
            FXMLLoader adminPaneLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/AdminMenuPane.fxml"));
            FXMLLoader kgalPaneLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/ManageKGALpane.fxml"));
            FXMLLoader scholarPeriodPaneLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/ManageScholarPeriodsPane.fxml"));
            
            try{
                Pane adminPane = adminPaneLoader.load();
                AdminMenuPaneController controller = (AdminMenuPaneController)adminPaneLoader.getController();
                controller.setUser(user);
                
                Pane kgalPane = kgalPaneLoader.load();
                ManageKGALpaneController kgalController = (ManageKGALpaneController)kgalPaneLoader.getController();
                kgalController.setUser(user);

                Pane scholarPeriodPane = scholarPeriodPaneLoader.load();
                ManageScholarPeriodsPaneController scholarPeriodsController = (ManageScholarPeriodsPaneController)scholarPeriodPaneLoader.getController();
                scholarPeriodsController.setUser(user);

                courseVBox.getChildren().add(adminPane);
                courseVBox.getChildren().add(kgalPane);
                courseVBox.getChildren().add(scholarPeriodPane);
            }catch(IOException exception){
                exception.printStackTrace();
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }
        if(AcademicBodyHead.class.isAssignableFrom(user.getClass())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/AcademicBodyHeadVBoxPane.fxml"));

            try{
                Pane academicBodyHeadPane = loader.load();
                AcademicBodyHeadVBoxPaneController controller = (AcademicBodyHeadVBoxPaneController)loader.getController();
                controller.setUser(user);

                courseVBox.getChildren().add(academicBodyHeadPane);
            }catch(IOException exception){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }

        if(Director.class.isAssignableFrom(user.getClass())){
            try{
                FXMLLoader chronogramPaneLoader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/DirectorChronogramPane.fxml"));
                Pane chronogramPane = chronogramPaneLoader.load();
                DirectorChronogramPaneController controller = (DirectorChronogramPaneController)chronogramPaneLoader.getController();
                controller.setDirector((Director)user);

                courseVBox.getChildren().add(chronogramPane);
            }catch(IOException exception){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }
    }
}