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
        loadHeader();
        loadResearches(0);
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
    private void goToReports(ActionEvent event) {
        
    }

    @FXML
    private void searchResearch(ActionEvent event) {
        researchVBox.getChildren().clear();
        loadResearches(1);
    }

    @FXML
    private void showValidated(ActionEvent event) {
        researchVBox.getChildren().clear();
        loadResearches(2);
    }

    private void loadHeader() {
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

    private void loadResearches(int type){
        ResearchDAO researchDAO = new ResearchDAO();
        
        try{
            switch(type) {
                case 0: {
                    researchList = researchDAO.getResearchProjectList();
                    break;
                }

                case 1: {
                    researchList = researchDAO.getSpecifiedResearchProjectList(reSearchTextField.getText());
                    break;
                }

                case 2: {
                    researchList = researchDAO.getValidatedResearchProjectList();
                    break;
                }

                default:
                    break;

            }
            
            for(ResearchProject research : researchList){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchVBoxPane.fxml"));
                    Pane pane = loader.load();
                    ResearchVBoxPaneController controller = (ResearchVBoxPaneController)loader.getController();
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
            exception.printStackTrace();
            Alert errorMessage = new Alert(AlertType.ERROR);
            errorMessage.setContentText("Error al cargar anteproyectos, verifique su conexión a internet y actualice este apartado");
            errorMessage.showAndWait();
        }
    }

}