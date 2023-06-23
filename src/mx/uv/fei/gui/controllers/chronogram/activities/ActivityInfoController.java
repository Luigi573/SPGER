package mx.uv.fei.gui.controllers.chronogram.activities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
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
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.FileDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.domain.Advance;
import mx.uv.fei.logic.domain.Course;
import mx.uv.fei.logic.domain.Director;
import mx.uv.fei.logic.domain.Professor;
import mx.uv.fei.logic.domain.User;
import mx.uv.fei.logic.domain.statuses.ActivityStatus;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ActivityInfoController{
    private Activity activity;
    private ArrayList<File> filesList;
    private ArrayList<File> deletedFilesList;
    private ArrayList<File> existingFilesList;
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
    private Button modifyDeliveryButton;
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
        filesList = new ArrayList();
        deletedFilesList = new ArrayList();
        existingFilesList = new ArrayList();
    }
    
    @FXML
    private void editActivity(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ModifyActivity.fxml"));
            Parent parent = loader.load();
            ModifyActivityController controller = (ModifyActivityController)loader.getController();
            controller.setActivity(activity);
            
            if(user != null){
                controller.setUser(user);
                controller.setCourse(course);
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
            if(file.length() < 	20971520){
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));
                    Pane pane = loader.load();
                    ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                    controller.setFile(file);
                    controller.hideDownloadButton();
 
                    fileVBox.getChildren().add(pane);
                    filesList.add(file);
                }catch(IOException exception) {
                    new AlertPopUpGenerator().showMissingFilesMessage();
                }
            }else{
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "Error al subir el archivo", "El archivo seleccionado es demasiado grande (Máximo: 20 Mb)");
            }
        }
    }
    
    @FXML
    private void deliverActivity(ActionEvent event) {
        if(!commentTextArea.getText().trim().isEmpty()){
            int result;
            int successfulSaves = 0;
            ArrayList<String> failedSaves = new ArrayList();
            ArrayList<String> failedCopiedFiles = new ArrayList();
            
            String activityDirectoryPath = System.getProperty("user.dir") + "\\Evidencias\\" + String.valueOf(user.getUserId()) + user.getFirstSurname() + user.getSecondSurname() + user.getName().replaceAll("\\s+", "") + "\\Actividades";
            File activityDirectory = new File(activityDirectoryPath);
            if (!activityDirectory.exists()) {
                if (!activityDirectory.mkdirs()) {
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", "No se pudo guardar la copia del archivo en el servidor.");
                }
            }
            
            for(File file : deletedFilesList){
                try{
                    FileDAO fileDAO = new FileDAO();
                    
                    file.delete();
                    fileDAO.removeActivityFile(file.getPath());
                }catch(DataDeletionException exception){
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al borrar la actividad", exception.getMessage());
                }
            }
            
            for (File file : filesList) {
                File fileCopy = new File(activityDirectoryPath + "\\" + file.getName());
                
                if (!fileCopy.exists()) {
                    try {
                        FileUtils.copyFile(file, fileCopy);
                        
                        FileDAO fileDAO = new FileDAO();
                        try {
                            fileResult = fileDAO.addActivityFile(fileCopy.getPath(), activity.getId());

                            if (fileResult > 0) {
                                successfulSaves = successfulSaves + 1;
                            }
                        } catch (DataInsertionException exception) {
                            failedSaves.add(file.getName());
                        }
                    } catch (DataInsertionException die) {
                        failedSaves.add(file.getName());
                    }
                }
            }

            for (String fileName : failedSaves) {
                new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.ERROR, "Error al guardar archivo", fileName);
            }
            
            try{
                ActivityDAO activityDAO = new ActivityDAO();
                if(activityDAO.setComment(commentTextArea.getText().trim(), activity.getId()) > 0){
                    new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.INFORMATION, "", "Entrega guardada exitosamente");
                    
                    goBack(event);
                }
            }catch(DataInsertionException exception){
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }else{
            new AlertPopUpGenerator().showCustomMessage(Alert.AlertType.WARNING, "No se puede guardar la información", "Debe definir un comentario para la actividad");
        }
    }
    
    @FXML
    private void modifyActivityDelivery(ActionEvent event){
        addAdvanceButton.setVisible(false);
        commentTextArea.setEditable(true);
        deliveryButton.setVisible(true);
        editButton.setVisible(false);
        uploadFileButton.setVisible(true);
        removeFilesButton.setVisible(true);
        
        for(Node node : fileVBox.getChildren()){
            Pane fileItemPane = (Pane)node;
            Button downloadButton = (Button)fileItemPane.lookup("#downloadFileButton");
            downloadButton.setVisible(false);
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/advances/CreateNewAdvance.fxml"));
            Parent parent = loader.load();
            CreateNewAdvanceController createNewAdvanceController = (CreateNewAdvanceController)loader.getController();
            createNewAdvanceController.setActivity(activity);
            createNewAdvanceController.setCourse(course);
            createNewAdvanceController.setUser(user);
            createNewAdvanceController.loadHeader();
            
            Scene scene = new Scene(parent);
            String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        } catch (IOException exeption) {
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    @FXML
    public void removeFiles(ActionEvent event) {
        if(activity.getStatus().equals(ActivityStatus.DELIVERED)){
            for(File file: existingFilesList){
                deletedFilesList.add(file);
            }
        }
        
        fileVBox.getChildren().clear();
        filesList.clear();
    }
    
    public void loadHeader(){
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

    
    public void loadAdvances(){
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
        
        if(activity.getStatus().equals(ActivityStatus.DELIVERED) || activity.getStatus().equals(ActivityStatus.REVIEWED )){
            addAdvanceButton.setVisible(false);
            commentTextArea.setEditable(false);
            deliveryButton.setVisible(false);
            editButton.setVisible(false);
            uploadFileButton.setVisible(false);
            removeFilesButton.setVisible(false);
        }
        
        if(!activity.getStatus().equals(ActivityStatus.DELIVERED)){
            modifyDeliveryButton.setVisible(false);
        }
    }
    
    public void setCourse(Course course){
        this.course = course;
    }
    
    public void setUser(User user){
        this.user = user;
        
        if(Professor.class.isAssignableFrom(user.getClass())){
            addAdvanceButton.setVisible(false);
            commentTextArea.setEditable(false);
            deliveryButton.setVisible(false);
            editButton.setVisible(false);
            modifyDeliveryButton.setVisible(false);
            removeFilesButton.setVisible(false);
            uploadFileButton.setVisible(false);
            
            if(Director.class.isAssignableFrom(user.getClass()) && !activity.getStatus().equals(ActivityStatus.ACTIVE)){
                feedbackButton.setVisible(true);
            }
        }else{
            feedbackButton.setText("Ver retroalimentación");
        }
    }
    
      private void loadActivityFiles(){
        ArrayList<Integer> fileIdList = new ArrayList();
        FileDAO fileDAO = new FileDAO();
        try {
            fileIdList = fileDAO.getFilesByActivity(activity.getId());
        } catch (DataRetrievalException exception) {
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }
        
        String path;
        File file;
        for (int fileId : fileIdList) {
            try {
                path = fileDAO.getFileByID(fileId).getFilePath();

                if (path != null) {
                    file = new File(path);
                    
                    try{
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ActivityFileItem.fxml"));
                        Pane pane = loader.load();
                        ActivityFileItemController controller = (ActivityFileItemController)loader.getController();
                        controller.setFile(file);
                        
                        fileVBox.getChildren().add(pane);
                        filesList.add(file);
                    } catch (IOException exception) {
                        new AlertPopUpGenerator().showMissingFilesMessage();
                    }
                }
            } catch (DataRetrievalException exception) {
                new AlertPopUpGenerator().showConnectionErrorMessage();
            }
        }
    }
    
}