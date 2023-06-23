package mx.uv.fei.gui.controllers.kgal;

import java.io.IOException;
import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.MainMenuController;
import mx.uv.fei.logic.daos.KGALDAO;
import mx.uv.fei.logic.domain.KGAL;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class KGALListController {
    private User user;
    
    @FXML
    private Pane headerPane;
    @FXML 
    private TextField searchTextField;
    @FXML
    private VBox kgalListVBox;
    
    @FXML
    private void searchKGALByDescription (ActionEvent event) {
        kgalListVBox.getChildren().clear();
        kgalListVBox.setSpacing(0);
        
        try {
            KGALDAO kgalDAO = new KGALDAO();
            ArrayList<KGAL> kgalList = kgalDAO.getKGALListByDescription(searchTextField.getText());
            for (KGAL kgal: kgalList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALListElement.fxml"));
                
                try{
                    Pane pane = loader.load();
                    KGALListElementController controller = (KGALListElementController)loader.getController();
                    controller.setUser(user);
                    controller.setKGAL(kgal);
                    
                    kgalListVBox.getChildren().add(pane);
                }catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    @FXML
    private void switchToCreateNewKGALScene(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/createNewKGAL.fxml"));
            Parent root = loader.load();
            CreateNewKGALController controller = (CreateNewKGALController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();
        
            Scene scene = new Scene(root);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene); 
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }    
    }
    
    @FXML
    private void exitKGALListScene(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/MainMenu.fxml"));
            Parent root = loader.load(); 
            MainMenuController controller = (MainMenuController)loader.getController();
            controller.setUser(user);
            controller.loadHeader();
            
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
    
    public void loadKgalList(){
        kgalListVBox.setSpacing(0);
        
        try {
            KGALDAO kgalDAO = new KGALDAO();
            ArrayList<KGAL> kgalList = kgalDAO.getKGALList();
            
            for (KGAL kgal: kgalList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALListElement.fxml"));
                
                try {
                    Pane pane = loader.load();
                    KGALListElementController controller = (KGALListElementController)loader.getController();
                    controller.setKGAL(kgal);
                    controller.setUser(user);
                    
                    kgalListVBox.getChildren().add(pane);
                } catch (IOException exception) {
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        } catch (DataRetrievalException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
    
    public void setUser(User user){
        this.user = user;
    }
}
