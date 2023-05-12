package mx.uv.fei.gui.controllers.chronogram;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import mx.uv.fei.gui.controllers.chronogram.ModifyActivityController;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.daos.FileDAO;

public class ActivityInfoController{
    private Activity activity;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    private ArrayList<File> filesList;
    
    @FXML
    private Button editButton;
    @FXML
    private Button homeButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private VBox vBoxActivityFileList;
    @FXML
    private TextFlow descriptionTextFlow;
    @FXML
    private Text descriptionText;
    
    @FXML
    public void initialize() {
        this.filesList = new ArrayList();
    }
    
    @FXML
    private void editActivity(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyActivity.fxml"));
            parent = loader.load();
            ModifyActivityController controller = (ModifyActivityController)loader.getController();
            controller.setAcitivty(activity);
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
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
    private void goHome(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
            parent = loader.load();
            
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(parent);
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
    public void setActivity(Activity activity){
        this.activity = activity;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText("Fecha inicio: " + activity.getStartDate());
        dueDateLabel.setText("Fecha fin: " + activity.getDueDate());
        descriptionText.setText(activity.getDescription());
    }
    
    @FXML
    public void uploadFileForDelivery(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el archivo a entregar");
        File file = fileChooser.showOpenDialog(stage);
        
        if (file != null) {
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setLabelText(file.getName());
                    vBoxActivityFileList.getChildren().add(pane);
                    this.filesList.add(file);
                } catch (IOException | IllegalStateException exception) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error al mostrar la información");
                    errorMessage.setContentText("Ocurrió un error al intentar mostrar la información.");
                    errorMessage.showAndWait();
                }
        }
    }
    
    @FXML
    public void deliverActivity(ActionEvent event) {
        int result;
        int successfulSaves = 0;
        ArrayList<String> failedSaves = new ArrayList();
        for (File file : filesList) {
            if (file != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(file.getPath());
                    if (result == 1) {
                        successfulSaves = successfulSaves + 1;
//                        Alert errorMessage = new Alert(Alert.AlertType.CONFIRMATION);
//                        errorMessage.setHeaderText("Operación exitosa");
//                        errorMessage.setContentText("El archivo se guardó correctamente");
//                        errorMessage.showAndWait();
                    }
                } catch (DataInsertionException die) {
                    failedSaves.add(file.getName());
//                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
//                    errorMessage.setHeaderText("Error al guardar archivo");
//                    errorMessage.setContentText(die.getMessage());
//                    errorMessage.showAndWait();
                }
            }
        }
        Alert successMessage = new Alert(Alert.AlertType.CONFIRMATION);
        successMessage.setHeaderText("Operación exitosa");
        successMessage.setContentText("Se guardaron correctamente " + successfulSaves + " archivos.");
        successMessage.showAndWait();
        
        for (String fileName : failedSaves) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error al guardar archivo");
            errorMessage.setContentText(fileName);
            errorMessage.showAndWait();
        }
    }
    
    public void removeFiles(ActionEvent event) {
        vBoxActivityFileList.getChildren().clear();
        filesList.clear();
    }
}