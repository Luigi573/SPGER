package mx.uv.fei.gui.controllers.research;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ResearchDAO;
import mx.uv.fei.logic.domain.ResearchProject;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ResearchManagerController{
    private ArrayList<ResearchProject> researchList;
    
    @FXML
    private Pane headerPane;
    @FXML
    private TextField reSearchTextField;
    @FXML
    private ScrollPane researchInfoScrollPane;
    @FXML
    private VBox researchVBox;

    @FXML
    private void initialize() {
        ResearchDAO researchDAO = new ResearchDAO();
        
        try{
            researchList = researchDAO.getResearchProjectList();
            
            for(ResearchProject research : researchList){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchVBoxPane.fxml"));
                    Pane pane = loader.load();
                    ResearchVBoxPaneController controller = loader.getController();
                    controller.setResearchProject(research);
                    controller.setContainer(researchInfoScrollPane);
                    
                    researchVBox.getChildren().add(pane);
                }catch(IOException exception){
                    Alert errorMessage = new Alert(AlertType.ERROR);
                    errorMessage.setContentText("Error al cargar, faltan archivos");
                    errorMessage.showAndWait();
                }
            }
        }catch(DataRetrievalException exception){
            Alert errorMessage = new Alert(AlertType.ERROR);
            errorMessage.setContentText("Error al cargar las actividades, verifique su conexión a internet y actualice este apartado");
            errorMessage.showAndWait();
        }

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
    @FXML
    void addResearch(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/AddResearch.fxml"));
        
        try{
            Parent parent = loader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.showAndWait();
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    @FXML
    void goToReports(ActionEvent event) {
        
    }
    @FXML
    void searchResearch(ActionEvent event) {
        
    }
    @FXML
    void showValidated(ActionEvent event) {
        
    }
}