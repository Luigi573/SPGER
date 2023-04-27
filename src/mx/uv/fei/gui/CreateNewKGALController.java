package mx.uv.fei.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateNewKGALController {

    private Stage stage;
    private Scene scene;
    private Parent root;
    
    @FXML
    private TextField tfDescription;

    @FXML
    void createNewKGAL(ActionEvent event) {
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

    @FXML
    void switchToKGALList(ActionEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/KGALList.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

}
