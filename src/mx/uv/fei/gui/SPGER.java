package mx.uv.fei.gui;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SPGER extends Application{
    @Override
    public final void start(Stage stage){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Chronogram.fxml"));
        
        try{
            Parent guiCronograma = loader.load();
            Scene scene = new Scene(guiCronograma);
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.show();
        }catch(IOException exception){
            System.out.println("Error al cargar programa SPGER");
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
