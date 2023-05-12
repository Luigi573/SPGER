package mx.uv.fei.gui.controllers.chronogram;

import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import mx.uv.fei.logic.daos.ActivityDAO;
import mx.uv.fei.logic.domain.Activity;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class ChronogramController{
    private Stage stage;
    private Scene scene;
    private Parent parent;
    
    @FXML
    private VBox activityListVBox;
    
    @FXML
    public void initialize(){
        ActivityDAO activityDAO = new ActivityDAO();
        
        try{
            ArrayList<Activity> activityList = activityDAO.getActivityList();
            
            for(Activity activity : activityList){               
                try{
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ChronogramActivityPane.fxml"));
                    Pane activityPane = loader.load();
                    ActivityPaneController controller = (ActivityPaneController)loader.getController();
                    controller.setActivity(activity);
                    
                    activityListVBox.getChildren().add(activityPane);
                }catch(IllegalStateException | IOException exception){
                    Alert errorMessage = new Alert(Alert.AlertType.ERROR);
                    errorMessage.setHeaderText("Error de carga");
                    errorMessage.setContentText("No se pudo abrir la ventana, verifique que el archivo .fxml esté en su ubicación correcta");
                    errorMessage.showAndWait();
                    exception.printStackTrace();
                }
            }
        }catch(DataRetrievalException exception){
            Alert errorMessage = new Alert(Alert.AlertType.ERROR);
            errorMessage.setHeaderText("Error de conexión");
            errorMessage.setContentText("Favor de verificar su conexión a internet e inténtelo de nuevo");
            errorMessage.showAndWait();
            exception.printStackTrace();
        }
    }
    @FXML
    private void createActivity(ActionEvent event){
        try{
            parent = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/CreateActivity.fxml")).load();
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
}