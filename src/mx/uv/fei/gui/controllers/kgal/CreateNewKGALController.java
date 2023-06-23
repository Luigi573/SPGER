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
    private Pane headerPane;
    @FXML
    private TextField descriptionTextField;

    @FXML
    public void createNewKGAL(ActionEvent event) {
        String newKGALDescription = tfDescription.getText();
        int result = 0;
        KGALDAO kgalDAO = new KGALDAO();
        KGAL newKgal = new KGAL();
        newKgal.setDescription(descriptionTextField.getText());
        
        try {
            KGALDAO kgalDAO = new KGALDAO();
            
            if(!kgalDAO.isBlank(newKgal)){
                if(kgalDAO.isValidDescription(newKgal)){
                    if(kgalDAO.addKGAL(newKgal) > 0) {
                        switchToKGALListScene(event);

                        new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se ha guardado la nueva LGAC correctamente.");
                    }
                }else{
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la LGAC", "La descripción es de máximo 40 caracteres");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la LGAC", "Falta agregar una descripción");
            }
        } catch (DataInsertionException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        descriptionTextField.clear();
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
