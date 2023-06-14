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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.gui.controllers.HeaderPaneController;
import mx.uv.fei.gui.controllers.chronogram.advances.CreateNewAdvanceController;
import mx.uv.fei.gui.controllers.chronogram.advances.AdvanceVBoxPaneController;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ActivityInfoController{
    private Activity activity;
    private ArrayList<File> filesList;
    private Course course;
    private User user;
    
    @FXML
    private Button addAdvanceButton;
    @FXML
    private Button feedbackButton;
    @FXML
    private Button deliveryButton;
    @FXML
    private Button editButton;
    @FXML
    private Button uploadFileButton;
    @FXML
    private Button removeFilesButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Pane headerPane;
    @FXML
    private TextArea commentTextArea;
    @FXML
    private Text descriptionText;
    @FXML
    private VBox fileVBox;
    @FXML
    private VBox advanceVBox;
    
    @FXML
    public void initialize() {
        filesList = new ArrayList<>();
    }
    
    @FXML
    private void editActivity(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ModifyActivity.fxml"));
            Parent parent = loader.load();
            ModifyActivityController controller = (ModifyActivityController)loader.getController();
            controller.setAcitivty(activity);
            
            if(user != null){
                controller.setUser(user);
            }
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
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
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ActivityFileItem.fxml"));
                Pane pane = loader.load();
                ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                controller.setFile(file);
                controller.hideDownloadButton();
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
        ArrayList<String> failedSaves = new ArrayList<>();
        
        for (File file : filesList) {
            if (file != null) {
                FileDAO fileDAO = new FileDAO();
                try {
                    result = fileDAO.addFile(file.getPath());
                    if (result > 0) {
                        successfulSaves = successfulSaves + 1;
                    }
                } catch (DataInsertionException die) {
                    failedSaves.add(file.getName());
                }
            }
        }
        
        new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.CONFIRMATION, "Operación exitosa", "Se guardaron correctamente " + successfulSaves + " archivos.");
        
        for (String fileName : failedSaves) {
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", fileName);
        }
    }
    
    @FXML
    private void openFeedbackPopUp(ActionEvent event){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/FeedbackPopUp.fxml"));
        
        try{
            Parent parent = loader.load();
            FeedbackPopUpController controller = (FeedbackPopUpController)loader.getController();
            controller.setActivity(activity);
            controller.setUser(user);
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
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
    
    @FXML
    private void createNewAdvance(ActionEvent event) {
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
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    @FXML
    public void removeFiles(ActionEvent event) {
        fileVBox.getChildren().clear();
        filesList.clear();
    }
    
    protected void loadHeader(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/HeaderPane.fxml"));
        
        try{
            if(user != null){
                Pane header = loader.load();
                HeaderPaneController controller = (HeaderPaneController)loader.getController();
                controller.setCourse(course);
                controller.setUser(user);
                
                headerPane.getChildren().setAll(header);
            }
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }

    
    protected void loadAdvances(){
        AdvanceDAO advanceDAO = new AdvanceDAO();
        
        try{
            ArrayList<Advance> advanceList  = advanceDAO.getActivityAdvanceList(activity.getId());
            
            for(Advance advance : advanceList){
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/AdvanceVBoxPane.fxml"));
                
                try{
                    Pane advancePane = loader.load();
                    AdvanceVBoxPaneController controller = (AdvanceVBoxPaneController)loader.getController();
                    controller.setAdvance(advance);
                    controller.setCourse(course);
                    controller.setUser(user);
                    
                    advanceVBox.getChildren().add(advancePane);
                }catch(IOException exception){
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
    }
        
    public void setActivity(Activity activity){
        this.activity = activity;
        titleLabel.setText(activity.getTitle());
        startDateLabel.setText(activity.getStartDate().toString());
        dueDateLabel.setText(activity.getDueDate().toString());
        descriptionText.setText(activity.getDescription());
    }
    
    protected void setCourse(Course course){
        this.course = course;
    }
    
    protected void setUser(User user){
        this.user = user;
        
        if(Professor.class.isAssignableFrom(user.getClass())){
            addAdvanceButton.setVisible(false);
            commentTextArea.setEditable(false);
            deliveryButton.setVisible(false);
            editButton.setVisible(false);
            uploadFileButton.setVisible(false);
            removeFilesButton.setVisible(false);
        }else{
            feedbackButton.setText("Ver retroalimentación");
        }
    }
}