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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ChronogramController{
    private Stage stage;
    private Scene scene;
    private Parent parent;
    
    @FXML
    private ComboBox<ResearchProject> studentChronogramComboBox;
    @FXML
    private Label chronogramTitleLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private VBox activityListVBox;
    
    @FXML
    public void initialize(){
        loadHeader();
        loadResearch();
    }

    @FXML
    private void createActivity(ActionEvent event){
        if(studentChronogramComboBox.getValue() != null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/CreateActivity.fxml"));
            try{
                parent = loader.load();
                CreateActivityController controller = (CreateActivityController)loader.getController();
                controller.setResearchId(studentChronogramComboBox.getValue().getId());
                
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(parent);
                stage.setTitle("SPGER");
                stage.setScene(scene);
                stage.show();
            }catch(IllegalStateException | IOException exception){
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Error de carga");
                errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                errorMessage.showAndWait();
            }
        }else{
            Alert warningMessage = new Alert(Alert.AlertType.WARNING);
            warningMessage.setHeaderText("Advertencia");
            warningMessage.setContentText("Favor de seleccionar un cronograma antes de crear una actividad");
            warningMessage.showAndWait();
        }
        
        
    }
    @FXML
    private void switchChronogram(ActionEvent event){
       if(studentChronogramComboBox.getValue() != null){
           loadActivities(studentChronogramComboBox.getValue().getId());
       }
    }
    
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }

    private void loadResearch(){
        ResearchDAO researchDAO = new ResearchDAO();
        
        try{
            ArrayList<ResearchProject> researchList = researchDAO.getResearchProjectList();
            
            for(ResearchProject research : researchList){
                if(research.getStudent().getName() != null){
                    studentChronogramComboBox.getItems().add(research);
                }
            }
        }catch(DataRetrievalException exception){
            //Log it
        }
    }
    private void loadActivities(int researchId){
        ActivityDAO activityDAO = new ActivityDAO();
        
        try{
            ArrayList<Activity> activityList = activityDAO.getActivityList(researchId);
            
            for(Activity activity : activityList){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ChronogramActivityPane.fxml"));
                
                try{
                    Pane activityPane = loader.load();
                    ActivityPaneController controller = (ActivityPaneController)loader.getController();
                    controller.setActivity(activity);
                    
                    activityListVBox.getChildren().add(activityPane);
                }catch(IllegalStateException | IOException exception){
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de carga");
                    errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                    errorMessage.showAndWait();
                }
            }
        }catch(DataRetrievalException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de conexión");
            errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
            errorMessage.showAndWait();
        }
    }
}