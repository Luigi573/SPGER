package mx.uv.fei.gui;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import mx.uv.fei.logic.domain.Activity;

public class ActivityPaneController{
    private Activity activity;
    private Stage stage;
    private Scene scene;
    private Parent parent;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Label startDateLabel;
    @FXML
    private Label titleLabel;
    
    @FXML
    private void viewActivity(ActionEvent event){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/chronogram/ActivityInfo.fxml"));
            parent = loader.load();
            ActivityInfoController controller = (ActivityInfoController)loader.getController();
            controller.setActivity(activity);
            
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
    }
}