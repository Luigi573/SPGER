package mx.uv.fei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage arg0) throws Exception{
        Parent login;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/Login.fxml"));
        login = loader.load();
        
        Scene scene = new Scene(login);
        Stage stage = new Stage();
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
