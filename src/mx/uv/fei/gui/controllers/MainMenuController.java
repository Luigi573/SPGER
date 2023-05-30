package mx.uv.fei.gui.controllers;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.CourseDAO;
import mx.uv.fei.logic.domain.AcademicBodyHead;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.DegreeBoss;
import mx.uv.fei.logic.domain.Professor;
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
        courseList = new ArrayList();
        loadHeader();
    }
    
    public void setUser(User user){
        this.user = user;
        
        loadHeader();
        loadCourses();
        loadSpecialPanes();
    }
    
    private void loadHeader(){
        headerPane.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            
            if(user != null){
                HeaderPaneController controller = (HeaderPaneController)loader.getController();
                controller.setUser(user);
            }
            
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    private void loadCourses(){
        CourseDAO courseDAO = new CourseDAO();
        
        try{
            courseList = courseDAO.getCourses();
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        for(Course course: courseList){
            if(Professor.class.isAssignableFrom(user.getClass()) && course.getProfessor().equals(user)){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/CourseHBoxPane.fxml"));
                
                try{
                    Pane pane = loader.load();
                    CourseHBoxPaneController controller = (CourseHBoxPaneController)loader.getController();
                    controller.setCourse(course);
                    
                    courseHBox.getChildren().add(pane);
                }catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }
    }
    private void loadSpecialPanes(){
        if(DegreeBoss.class.isAssignableFrom(user.getClass())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/AdminMainMenuPane.fxml"));
            
            try{
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                
                stage.show();
            }catch(IOException exception){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }else if(AcademicBodyHead.class.isAssignableFrom(user.getClass())){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/AcademicBodyHeadVBoxPane.fxml"));
            
            try{
                Parent parent = loader.load();
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                
                stage.show();
            }catch(IOException exception){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }
    }
}
