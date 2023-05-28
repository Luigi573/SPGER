package mx.uv.fei.gui.controllers.chronogram;

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
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.File;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class AdvanceInfoController {
    private Advance advance;
    private Activity activity;

    @FXML
    private Text advanceCommentsText;

    @FXML
    private Pane advanceFilePane;

    @FXML
    private Pane headerPane;
    
    @FXML
    private VBox advanceFileVBox;

    @FXML
    private Label advanceTitleLabel;

    @FXML
    private Button modifyAdvanceButton;

    @FXML
    private Button returnButton;

    @FXML 
    public void initialize() {
        loadHeader();    
    }
    
    @FXML
    private void modifyAdvance(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyAdvance.fxml"));
            Parent parent = loader.load();
            ModifyAdvanceController controller = (ModifyAdvanceController)loader.getController();
            controller.setAdvance(advance);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }

    @FXML
    private void returnToAdvanceList(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(ActivityInfoController.activity);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de carga");
            errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
            errorMessage.showAndWait();
        }
    }
    
    public void setAdvanceInfo(Advance advance) {
        this.advance = advance;
        advanceTitleLabel.setText(advance.getTitle());
        advanceCommentsText.setText(advance.getComments());
        
        FileDAO fileDAO = new FileDAO();
        File file;
        
        if(advance.getFileID() != 0) {
            try {
                file = fileDAO.getFileByID(advance.getFileID());
                
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));               
                try {
                    Pane advancePane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file.getFilePath());

                    advanceFileVBox.getChildren().add(advancePane);
                } catch(IllegalStateException | IOException exception){
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de carga");
                    errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                    errorMessage.showAndWait();
                }
            } catch (DataRetrievalException exception) {
                Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                errorMessage.setHeaderText("Error de conexión");
                errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
                errorMessage.showAndWait();
            }
        }
    }
    
    public void setActivity() {
        this.activity = ActivityInfoController.activity;
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

}
