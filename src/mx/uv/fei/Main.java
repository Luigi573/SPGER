package mx.uv.fei;

import java.util.HashMap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.uv.fei.gui.AlertPopUpGenerator;
import mx.uv.fei.logic.daos.UserDAO;
import mx.uv.fei.logic.exceptions.DataRetrievalException;

public class Main extends Application{
    @Override
    public void start(Stage arg0){
        try{
            FXMLLoader loader;
            Parent guiUsuarios;
            UserDAO userDAO = new UserDAO();
            if(userDAO.hasUsersInTheDatabase()){
                loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/Login.fxml"));
                guiUsuarios = loader.load();
            }else{
                loader = new FXMLLoader(getClass().getResource("/mx/uv/fei/gui/fxml/users/RegisterAdmin.fxml"));
                guiUsuarios = loader.load();
            }

            Scene scene = new Scene(guiUsuarios);
            String css = getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
            scene.getStylesheets().add(css);
            Stage stage = new Stage();
            stage.setTitle("SPGER");
            stage.setScene(scene);
            stage.setResizable(false);

            Image icon = new Image("./dependencies/images/spgerLogo.jpeg");
            stage.getIcons().add(icon);

            stage.show();
        }catch(DataRetrievalException exception){
            new AlertPopUpGenerator().showConnectionErrorMessage();
        }catch(IOException exception){
            new AlertPopUpGenerator().showMissingFilesMessage();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    HashMap<String, String> coeeida = new HashMap<>(0, 0);
}
