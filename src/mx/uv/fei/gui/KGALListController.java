package mx.uv.fei.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class KGALListController {
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML 
    private TextField tfDescription;
    @FXML
    private VBox vBoxKGALList;
    @FXML
    private HBox hBoxKGALListElement;
    @FXML
    private Label lKGALID;
    @FXML
    private Label IKGALDescription;
    
    public void showKGALList() {       
        Label label = new Label("Hola");
        //hBoxKGALListElement.getChildren().add(label);
        vBoxKGALList.getChildren().add(label);
    }
    
    public void createNewKGAL(ActionEvent event) {
        String newKGALDescription = tfDescription.getText();
        KGALDAO kgalDAO = new KGALDAO();
        KGAL newKgal = new KGAL();
        newKgal.setDescription(newKGALDescription);
        try {
            kgalDAO.addKGAL(newKgal);
        } catch (DataInsertionException die) {
            System.out.println(die.getMessage());
        }
        tfDescription.clear();
    }
    
    public void switchToKGALList(ActionEvent event) throws IOException {
        System.out.println("Going back...");
        root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/KGALList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void switchToCreateNewKGAL(ActionEvent event) throws IOException {
        System.out.println("Creating new KGAL...");
        root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/createNewKGAL.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        
    }
    
    public void exitKGALList(ActionEvent event) throws IOException {
        System.out.println("Going back...");
        root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/KGALList.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
