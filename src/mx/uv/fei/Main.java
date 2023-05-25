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
            //getClass().getResource("/mx/uv/fei/gui/fxml/kgal/KGALList.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/research/ResearchManager.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/chronogram/ModifyActivity.fxml")// tha xavier gui
            //getClass().getResource("/mx/uv/fei/gui/fxml/courses/students/GuiUsersCourse.fxml")
            getClass().getResource("/mx/uv/fei/gui/fxml/courses/GuiCourses.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/users/GuiUsers.fxml")
            //getClass().getResource("/mx/uv/fei/gui/fxml/reports/GuiResearchReport.fxml")
        );
        guiUsuarios = loader.load();

        //TODO
        //Nombres y apellidos en userspanes y studentadder en GuiUsersCourse y GuiStudentAdder GG
        //Mostrar estatus de usuarios en comboboxes para registrar usuarios en GuiRegisterUsers y ModifyUserPane GG
        //Checar lo de estudiantes activos y disponibles con xavier GG
        //Detalles estéticos en GuiUsers cuando cambias la matricula a nombre de personal y viceversa GG
        //Crear enum con tipo de usuario para comboboxes GG
        //Al abrir GuiResearchReport en ResearchManager no debe de salir como ventana emergente GG
        //Color de bordes de botones de cursos, usuarios y anteproyectos GG
        //Cambiar la clase de ventanas emergentes a alertpopup GG
        //Seguir el standar GG
        //Quitar los this innecesarios GG

        //Eliminar printstacktraces de los daos
        //Probar las guis de usuario como si no hibiera un mañana
        //Pruebas
        
        Scene scene = new Scene(guiUsuarios);
        String css = this.getClass().getResource("/mx/uv/fei/gui/stylesfiles/Styles.css").toExternalForm();
        scene.getStylesheets().add(css);
        Stage stage = new Stage();
        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    public static void main (String args[]) {
        launch(args);
    }
}
