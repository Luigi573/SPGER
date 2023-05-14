package mx.uv.fei.gui.controllers.kgal;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.daos.KGALDAO;

import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class KGALListController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML 
    private TextField tfSearch;
    
    @FXML
    private VBox vBoxKGALList;
    
    @FXML
    public void initialize() {
        KGALDAO kgalDAO = new KGALDAO();
        vBoxKGALList.setSpacing(0);
        ArrayList<KGAL> kgalList;
        
        try {
             kgalList = kgalDAO.getKGALList();
            for (KGAL kgal: kgalList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/KGALListElement.fxml"));
                    Pane pane = loader.load();
                    KGALListElementController controller = (KGALListElementController)loader.getController();
                    controller.setLabelText(kgal);
                    controller.setKGAL(kgal);
                    vBoxKGALList.getChildren().add(pane);
                } catch (IOException | IllegalStateException exception) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al mostrar la información");
                    errorMessage.setContentText("Ocurrió un error al intentar mostrar la información.");
                    errorMessage.showAndWait();
                }
            }
            
        } catch (DataRetrievalException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al recuperar la información.");
            errorMessage.setContentText(exception.getMessage());
            errorMessage.showAndWait();
        }
        
    }   
    
    public void searchKGALByDescription (ActionEvent event) {
        vBoxKGALList.getChildren().clear();
        KGALDAO kgalDAO = new KGALDAO();
        vBoxKGALList.setSpacing(0);
        ArrayList<KGAL> kgalList;
        String description;
        description = tfSearch.getText();

        
        try {
             kgalList = kgalDAO.getKGALListByDescription(description);
            for (KGAL kgal: kgalList) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/KGALListElement.fxml"));
                    Pane pane = loader.load();
                    KGALListElementController controller = (KGALListElementController)loader.getController();
                    controller.setLabelText(kgal);
                    
                    vBoxKGALList.getChildren().add(pane);
                } catch (IOException | IllegalStateException e) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al cargar los elementos de la ventana.");
                    errorMessage.setContentText(e.getMessage());
                    errorMessage.showAndWait();
                }
            }
            
        } catch (DataRetrievalException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al recuperar la información de la Base de Datos");
            errorMessage.setContentText(exception.getMessage());            
            errorMessage.showAndWait();
        }
    }
           
    public void switchToCreateNewKGALScene(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/createNewKGAL.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene); 
        stage.show();       
    }
    
    public void exitKGALListScene(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/fxml/manageKGAL/KGALList.fxml")); //should point to main menu
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al mostrar la ventana.");
            errorMessage.setContentText(e.getMessage());
            errorMessage.showAndWait();
        }
    }
   
}
