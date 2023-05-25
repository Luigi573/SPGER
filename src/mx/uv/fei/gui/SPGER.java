package mx.uv.fei.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SPGER extends Application{
    @Override
    public final void start(Stage stage){
        //FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/Chronogram.fxml"));
        
        try{
            Parent guiCronograma = loader.load();
            Scene scene = new Scene(guiCronograma);
            stage.setTitle("SPGER");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(IllegalStateException | IOException exception){
            Alert errorMessage = new Alert(AlertType.ERROR);
            errorMessage.setTitle("Error");
            errorMessage.setContentText("Hubo un error al cargar el sistema SPGER, no se encontraron los archivos");
            errorMessage.showAndWait();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}