package mx.uv.fei.gui.controllers.kgal;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class UpdateKGALController {
    private KGAL kgal;
    private User user;
    
    @FXML
    private Label idLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private TextField descriptionTextField;

    @FXML
    public void initialize() {
    }
    
    @FXML
    private void updateKGALDescription(ActionEvent event) {
        kgal.setDescription(descriptionTextField.getText());
        int result = 0;
        KGALDAO kgalDAO = new KGALDAO();
        
        try {
            if(!kgalDAO.isBlank(kgal)){
                if(kgalDAO.isValidDescription(kgal)){
                    
                    result = kgalDAO.updateKGALDescription(kgal.getKgalID(), kgal.getDescription());
                    if(result > 0) {
                        new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se ha guardado la nueva LGAC correctamente.");
                        
                        switchToKGALListScene(event);
                    }
                }else{
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la LGAC", "La descripción es de máximo 40 caracteres");
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede crear la LGAC", "Falta agregar una descripción");
            }
        } catch (DataInsertionException dre) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
    }

    @FXML
    private void switchToKGALListScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml"));
        
        try {
            Parent root = loader.load();
            KGALListController controller = (KGALListController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();
            controller.loadKgalList();
            
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException exception) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

    public void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            HeaderPaneController controller = (HeaderPaneController)loader.getController();
            controller.setUser(user);
            
            headerPane.getChildren().setAll(header);
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public void setKGAL(KGAL kgal) {
        this.kgal = kgal;
        
        descriptionTextField.setText(kgal.getDescription());
        idLabel.setText(Integer.toString(kgal.getKgalID()));
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
