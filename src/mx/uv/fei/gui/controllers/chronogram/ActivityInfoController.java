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
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ActivityInfoController{
    static Activity activity;

    private ArrayList<File> filesList;
    
    @FXML
    private Label titleLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private VBox activityFileListVBox;
    @FXML
    private VBox advanceListVBox;
    @FXML
    private Text descriptionText;
    
    @FXML
    public void initialize() {
        this.filesList = new ArrayList();
        loadHeader();
        
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
        try {
            ArrayList<Advance> advancesList = advanceDAO.getAdvancesList();
            
            for (Advance advance : advancesList) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/AdvanceItem.fxml"));
                
                try {
                    Pane activityPane = loader.load();
                    AdvanceItemController controller = (AdvanceItemController)loader.getController();
                    controller.setAdvance(advance);
                    
                    advanceListVBox.getChildren().add(activityPane);
                } catch (IllegalStateException | IOException exception) {
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de carga");
                    errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                    errorMessage.showAndWait();
                }
            }
        } catch (DataRetrievalException exception) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de conexión");
            errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
            errorMessage.showAndWait();
        }
    }
    @FXML
    private void editActivity(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyActivity.fxml"));
            Parent parent = loader.load();
            ModifyActivityController controller = (ModifyActivityController)loader.getController();
            controller.setAcitivty(activity);
            
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
    private void goHome(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
            Parent parent = loader.load();
            
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
    public void uploadFileForDelivery(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el archivo a entregar");
        File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        
        if (file != null) {
            try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setLabelText(file.getName());
                    activityFileListVBox.getChildren().add(pane);
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
                    }
                } catch (DataInsertionException die) {
                    failedSaves.add(file.getName());
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
    
    @FXML
    public void removeFiles(ActionEvent event) {
        activityFileListVBox.getChildren().clear();
        filesList.clear();
    }
    
    @FXML
    private void feedback(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = (FeedbackPopUpController)loader.getController();
            controller.setActivity(activity);
            
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setResizable(false);
            
            stage.showAndWait();
        }catch(IOException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    
    @FXML
    void createNewAdvance(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/CreateNewAdvance.fxml"));
            Parent parent = loader.load();
            CreateNewAdvanceController createNewAdvanceController = (CreateNewAdvanceController)loader.getController();
            createNewAdvanceController.setActivity(activity);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exeption) {
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setContentText("Error al cargar, faltan archivos");
            errorMessage.showAndWait();
        }
    }
    
    private void loadHeader(){
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
    
    public void setActivity(Activity activity){
        ActivityInfoController.activity = activity;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText("Fecha inicio: " + activity.getStartDate());
        dueDateLabel.setText("Fecha fin: " + activity.getDueDate());
        descriptionText.setText(activity.getDescription());
    }
}