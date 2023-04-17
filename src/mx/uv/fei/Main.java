package mx.uv.fei;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.embed.swt.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import mx.uv.fei.dataaccess.DataBaseManager;
import mx.uv.fei.logic.exceptions.DataInsertionException;
import mx.uv.fei.logic.exceptions.DataRetrievalException;
import mx.uv.fei.logic.daos.AcademicBodyDAO;
import mx.uv.fei.logic.daos.AdvanceDAO;
import mx.uv.fei.logic.daos.ReceptionalWorkDAO;


public class Main extends Application{    

    @Override
    public void start(Stage arg0) throws Exception {
        Parent guiReporteAnteproyecto;
        Path path = Paths.get("/home/luisalonso/Documentos/SPGER/src/mx/uv/fei/gui/fxmlfiles/guireporteanteproyecto/GuiReporteAnteproyecto.fxml");
        //Path path = Paths.get("src/mx/uv/fei/gui/fxmlfiles/GuiReporteAnteproyecto.fxml");
        System.out.println(path.toAbsolutePath().toString());
        FXMLLoader loader = new FXMLLoader(
            //getClass().getResource(path.toAbsolutePath().toString())
            getClass().getResource("gui/fxmlfiles/guireporteanteproyecto/GuiReporteAnteproyecto.fxml")
            //getClass().getResource("gui/fxmlfiles/guireporteanteproyecto/GuiReporteAnteproyecto.fxml")
        );
        System.out.println(loader.getLocation());
        guiReporteAnteproyecto = loader.load();

        Scene scene = new Scene(guiReporteAnteproyecto);
        Stage stage = new Stage();

        stage.setTitle("SPGER");
        stage.setScene(scene);
        stage.show();
    }

    public static void main (String args[]) {
        launch(args);
    }
}
