package mx.uv.fei;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{
    @Override
    public void start(Stage arg0) throws Exception{
        Parent guiUsuarios;
        FXMLLoader loader = new FXMLLoader(
            //getClass().getResource("/mx/uv/fei/gui/fxml/Login.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/activities/ModifyActivity.fxml")// tha xavier gui
            getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiCourses.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/reports/GuiResearchReport.fxml")
        );
        guiUsuarios = loader.load();
        //TODO

        //Agregar asteriscos con el nombre campos obligatorios en gui usuarios y cursos
        //Si se puede modificar curso sin periodo escolar
        //Que funcione la integracion
        //Encriptar properties
        //Preguntarle al xavier sobre que onda con los cronogramas
        //Xavier, speger es boton para regresar? 
        
        Scene scene = new Scene(guiUsuarios);
        String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        scene.getStylesheets().add(css);
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
