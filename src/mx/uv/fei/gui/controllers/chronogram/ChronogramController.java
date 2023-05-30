package mx.uv.fei.gui.controllers.chronogram;

import mx.uv.fei.gui.controllers.chronogram.activities.CreateActivityController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityPaneController;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ChronogramController{
    private Course course;
    private User user;
    
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
        if(studentChronogramComboBox.getValue() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/CreateActivity.fxml"));
            try{
                Parent parent = loader.load();
                CreateActivityController controller = (CreateActivityController)loader.getController();
                controller.setResearchId(studentChronogramComboBox.getValue().getId());
                
                Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                Scene scene = new Scene(parent);
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
            chronogramTitleLabel.setText("Cronograma de " + studentChronogramComboBox.getValue().getStudent().getName());
            ActivityDAO activityDAO = new ActivityDAO();
        
            try{
                ArrayList<Activity> activityList = activityDAO.getActivityList(studentChronogramComboBox.getValue().getId());

                for(Activity activity : activityList){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ChronogramActivityPane.fxml"));

                    try{
                        Pane activityPane = loader.load();
                        ActivityPaneController controller = (ActivityPaneController)loader.getController();
                        controller.setActivity(activity);

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
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void loadComboBoxResearch(){
        studentChronogramComboBox.getItems().clear();
        ResearchDAO researchDAO = new ResearchDAO();
        
        try{
            ArrayList<ResearchProject> researchList = researchDAO.getResearchProjectList();
            
            for(ResearchProject research : researchList){
                if(research.getStudent().getName() != null){
                    studentChronogramComboBox.getItems().add(research);
                }
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    private void setUser(User user){
        
    }
    private void setCourse(Course course){
        
    }
}