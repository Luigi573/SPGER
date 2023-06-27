package mx.uv.fei.gui.controllers.chronogram;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.MainMenuController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityPaneController;
import mx.uv.fei.gui.controllers.chronogram.activities.CreateActivityController;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.Student;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ChronogramController{
    private Course course;
    private int researchId;
    private User user;
    
    @FXML
    private Button createActivityButton;
    @FXML
    private ComboBox<ResearchProject> studentChronogramComboBox;
    @FXML
    private Label chronogramTitleLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private VBox activityListVBox;
    
    @FXML
    private void createActivity(ActionEvent event){
        if(researchId != 0){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/CreateActivity.fxml"));
            try{
                Parent parent = loader.load();
                CreateActivityController controller = (CreateActivityController)loader.getController();
                controller.setResearchId(researchId);
                controller.setCourse(course);
                controller.setUser(user);
                controller.loadHeader();
                
                Scene scene = new Scene(parent);
                String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
                scene.getStylesheets().add(css);
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                stage.setTitle("SPGER");
                stage.setScene(scene);
                stage.show();
            }catch(IOException exception){
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "Advertencia", "Favor de seleccionar un cronograma antes de crear una actividad");
        }
    }
    @FXML
    private void switchChronogram(ActionEvent event){
       if(studentChronogramComboBox.getValue() != null){
            if(studentChronogramComboBox.getValue().getStudents().get(0).getName() != null){
                if(studentChronogramComboBox.getValue().getStudents().get(1).getName() != null ){
                    chronogramTitleLabel.setText("Cronograma de " + studentChronogramComboBox.getValue().getStudents().get(0).getName() + " & "
                            + studentChronogramComboBox.getValue().getStudents().get(1).getName());
                }else{
                    chronogramTitleLabel.setText("Cronograma de " + studentChronogramComboBox.getValue().getStudents().get(0).getName());
                }
            }else{
                chronogramTitleLabel.setText("Cronograma sin asignar");
            }
            
            activityListVBox.getChildren().clear();
            ActivityDAO activityDAO = new ActivityDAO();
            researchId = studentChronogramComboBox.getValue().getId();
        
            try{
                ArrayList<Activity> activityList = activityDAO.getActivityList(researchId);

                for(Activity activity : activityList){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ChronogramActivityPane.fxml"));

                    try{
                        Pane activityPane = loader.load();
                        ActivityPaneController controller = (ActivityPaneController)loader.getController();
                        controller.setActivity(activity);
                        controller.setUser(user);
                        controller.setCourse(course);

                        activityListVBox.getChildren().add(activityPane);
                    }catch(IOException exception){
                        new AlertPopUpGenerator().showMissingFilesMessage();
                    }
                }
            }catch(DataRetrievalException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
       }
    }
    
    @FXML
    private void goBack(ActionEvent event){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/MainMenu.fxml"));
            Parent parent = loader.load();
            MainMenuController controller = (MainMenuController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();

            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){    
        if(Student.class.isAssignableFrom(user.getClass())){
            setStudentView((Student)user);
        }else if(Professor.class.isAssignableFrom(user.getClass())){
            if(course != null){
                setProfessorView((Professor)user, course);
            }else{
                setDirectorView((Director)user);
            }
        }
    }
    
    private void setDirectorView(Director director){
        chronogramTitleLabel.setText("Cronograma");
        createActivityButton.setVisible(true);
        this.user = director;
        
        try{
            ResearchDAO researchDAO = new ResearchDAO();
            loadComboBoxResearch(researchDAO.getDirectorsResearch(director.getStaffNumber()));
        }catch(DataRetrievalException exception){
            exception.printStackTrace();
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    private void setProfessorView(Professor professor, Course course){
        chronogramTitleLabel.setText("Cronograma");
        this.course = course;
        this.user = professor;
        
        createActivityButton.setVisible(false);
        
        try{
            ResearchDAO researchDAO = new ResearchDAO();
            
            loadComboBoxResearch(researchDAO.getCourseResearch(course.getNrc()));
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    private void setStudentView(Student student){
        this.user = student;
        chronogramTitleLabel.setText("Mi cronograma");
        studentChronogramComboBox.setVisible(false);

        try{
            ActivityDAO activityDAO = new ActivityDAO();
            ResearchDAO researchDAO = new ResearchDAO();
            
            ResearchProject research = researchDAO.getStudentsResearch(student.getMatricle());
            researchId = research.getId();
            ArrayList<Activity> activityList = activityDAO.getActivityList(researchId);

            for(Activity activity : activityList){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ChronogramActivityPane.fxml"));

                try{
                    Pane activityPane = loader.load();
                    ActivityPaneController controller = (ActivityPaneController)loader.getController();
                    controller.setActivity(activity);
                    controller.setCourse(course);
                    controller.setUser(user);

                    activityListVBox.getChildren().add(activityPane);
                }catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController controller = (HeaderPaneController)loader.getController();
            controller.setUser(user);
            controller.setCourse(course);
            
            headerPane.getChildren().clear();
            headerPane.getChildren().add(header);
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void loadComboBoxResearch(ArrayList<ResearchProject> researchList){
        studentChronogramComboBox.getItems().clear();
        
        for(ResearchProject research : researchList){
            studentChronogramComboBox.getItems().add(research);
        }
    }
}