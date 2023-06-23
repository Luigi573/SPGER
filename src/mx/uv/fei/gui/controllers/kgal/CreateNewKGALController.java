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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateNewKGALController {
    private User user;
    
    @FXML
    private Pane headerPane;
    @FXML
    private TextField tfDescription;

    @FXML
    public void createNewKGAL(ActionEvent event) {
        KGAL newKgal = new KGAL();
        newKgal.setDescription(tfDescription.getText());
        
        try {
            KGALDAO kgalDAO = new KGALDAO();
            
            if(kgalDAO.addKGAL(newKgal) > 0) {
                switchToKGALListScene(event);
                
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se ha guardado la nueva LGAC correctamente.");
            }
        } catch (DataInsertionException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        tfDescription.clear();
    }

    @FXML
    public void switchToKGALListScene(ActionEvent event) {
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
        } catch (IOException e) {
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
    
    public void setUser(User user){
        this.user = user;
    }
}
