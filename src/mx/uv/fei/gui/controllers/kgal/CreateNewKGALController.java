package mx.uv.fei.gui.controllers.kgal;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateNewKGALController {
    
    @FXML
    private TextField tfDescription;

    @FXML
    public void createNewKGAL(ActionEvent event) {
        String newKGALDescription = tfDescription.getText();
        int result = 0;
        KGALDAO kgalDAO = new KGALDAO();
        KGAL newKgal = new KGAL();
        newKgal.setDescription(newKGALDescription);
        try {
            result = kgalDAO.addKGAL(newKgal);
            System.out.println(result);
        } catch (DataInsertionException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        if(result > 0) {
            Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
            successMessage.setHeaderText("Operación exitosa");
            successMessage.setContentText("Se ha guardado la nueva LGAC correctamente.");
            successMessage.showAndWait();
        }
        tfDescription.clear();
    }

    @FXML
    public void switchToKGALListScene(ActionEvent event) {
        try {

            Parent root = FXMLLoader.load(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml"));
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

}
