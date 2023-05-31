package mx.uv.fei.gui.controllers.chronogram.activities;

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
import javafx.stage.Modality;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.chronogram.advances.AdvanceVBoxPaneController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ActivityInfoController{
    private Activity activity;
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
    private Text descriptionText;
    @FXML
    private VBox fileVBox;
    @FXML
    private VBox advanceVBox;
    
    @FXML
    public void initialize() {
        filesList = new ArrayList();
        loadHeader();
    }
    @FXML
    private void editActivity(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ModifyActivity.fxml"));
            Parent parent = loader.load();
            ModifyActivityController controller = (ModifyActivityController)loader.getController();
            controller.setAcitivty(activity);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(parent);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    @FXML
    private void uploadFileForDelivery(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Seleccione el archivo a entregar");
        File file = fileChooser.showOpenDialog((Stage)((Node)event.getSource()).getScene().getWindow());
        
        if (file != null){
            try{
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));
                Pane pane = loader.load();
                ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                controller.setLabelText(file.getName());
                fileVBox.getChildren().add(pane);
                filesList.add(file);
            }catch(IOException exception) {
                new AlertPopUpGenerator().showMissingFilesMessage();
            }
        }
    }
    @FXML
    private void deliverActivity(ActionEvent event) {
        int result;
        int successfulSaves = 0;
        ArrayList<String> failedSaves = new ArrayList();
        for (File file : filesList) {
            if (file != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(file.getPath());
                    if (result > 0) {
                        successfulSaves = successfulSaves + 1;
                    }
                } catch (DataInsertionException exception) {
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
        fileVBox.getChildren().clear();
        filesList.clear();
    }
    @FXML
    private void feedback(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = (FeedbackPopUpController)loader.getController();
            controller.setActivity(activity);
            
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            
            stage.setScene(scene);
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)((Node)event.getSource()).getScene().getWindow());
            stage.setResizable(false);
            
            stage.showAndWait();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    private void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            Pane header = loader.load();
            headerPane.getChildren().add(header);
            
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    public void setActivity(Activity activity){
        this.activity = activity;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText("Fecha inicio: " + activity.getStartDate());
        dueDateLabel.setText("Fecha fin: " + activity.getDueDate());
        descriptionText.setText(activity.getDescription());
        
        loadAdvances();
    }
    private void loadAdvances(){
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
        try{
            ArrayList<Advance> advanceList  = advanceDAO.getAdvanceList(activity.getId());
            
            for(Advance advance : advanceList){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/AdvanceVBoxPane.fxml"));
                
                try{
                    Pane advancePane = loader.load();
                    AdvanceVBoxPaneController controller = (AdvanceVBoxPaneController)loader.getController();
                    controller.setAdvance(advance);
                    
                    advanceVBox.getChildren().add(advancePane);
                }catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
}