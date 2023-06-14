package mx.uv.fei.gui.controllers.chronogram.advances;

import java.io.File;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityFileItemController;
import mx.uv.fei.gui.controllers.chronogram.activities.ActivityInfoController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataInsertionException;

public class CreateNewAdvanceController {
    
    private Activity activity;
    private String filePath;

    @FXML
    private TextArea advanceCommentsTextArea;

    @FXML
    private TextField advanceTitleTextField;

    @FXML
    private Button createAdvanceButton;

    @FXML
    private Button returnButton;

    @FXML
    private Button uploadFileButton;
    
    @FXML
    private VBox advanceFileVBox;

    @FXML
    void createAdvance(ActionEvent event) {
        int savedFileGeneratedId;
        savedFileGeneratedId = saveFile();
        
        Advance advance = new Advance();
        advance.setActivityID(this.activity.getId());
        advance.setFileID(savedFileGeneratedId);
        advance.setTitle(advanceTitleTextField.getText());
        advance.setComment(advanceCommentsTextArea.getText());
        advance.setState("Sin revisar"); //TODO

        System.out.println(advance);
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        int result = 0;
        try {
            result = advanceDAO.addAdvance(advance);
        } catch (DataInsertionException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Ocurrió un error");
            errorMessage.setContentText(exception.getMessage());
            errorMessage.showAndWait(); 
        } finally {
            if (result > 0) {
                Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
                successMessage.setHeaderText("Operación exitosa");
                successMessage.setContentText("Se ha guardado el nuevo avance correctamente.");
                successMessage.showAndWait();
            }
        }
    }

    @FXML
    void returnToAdvanceList(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityInfo.fxml"));
            Parent parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(this.activity);
            
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
    void uploadFileToAdvance(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el archivo a entregar");
        File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        
        if (file != null) {
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file);
                    advanceFileVBox.getChildren().add(pane);
                    this.filePath = file.getPath();
                } catch (IOException | IllegalStateException exception) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al mostrar la información");
                    errorMessage.setContentText("Ocurrió un error al intentar mostrar la información.");
                    errorMessage.showAndWait();
                }
        }
    }

    public int saveFile() {
        int result = 0;
        if (this.filePath != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(this.filePath);
                } catch (DataInsertionException die) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al guardar archivo");
                    errorMessage.setContentText("Ocurrió un error al intentar guardar el archivo. Por favor intente de nuevo más tarde.");
                    errorMessage.showAndWait(); 
                }
        }
        return result;
    }
    
    public void setActivity(Activity activity) {
        this.activity = activity;
    }
}
