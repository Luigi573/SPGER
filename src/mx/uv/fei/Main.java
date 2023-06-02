package mx.uv.fei;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;

public class Main extends Application{
    @Override
    public final void start(Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/Login.fxml"));
        try{
            Parent guiCronograma = loader.load();
            Scene scene = new Scene(guiCronograma);
            stage.setTitle("SPGER");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
