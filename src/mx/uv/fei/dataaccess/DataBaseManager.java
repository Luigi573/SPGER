package mx.uv.fei.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DataBaseManager {
    private Connection connection;
    private Path path;
    private InputStream fis;
    private Properties dataBaseUserPropertiesFile;

    public DataBaseManager(){
        try {
            path = Paths.get("src/dependencies/resources/DatabaseAccess.properties");
            fis = Files.newInputStream(path.toAbsolutePath());
            dataBaseUserPropertiesFile = new Properties();
            dataBaseUserPropertiesFile.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void connect() throws SQLException{
        connection = DriverManager.getConnection(
            this.dataBaseUserPropertiesFile.getProperty("DATABASE_NAME"),
            this.dataBaseUserPropertiesFile.getProperty("DATABASE_USER"),
            this.dataBaseUserPropertiesFile.getProperty("DATABASE_PASSWORD")
        );
    }

    public Connection getConnection() throws SQLException {
        this.connect();
        return connection;
    }

    public void closeConnection(){
        if(connection != null){
            try{
                if(!connection.isClosed()){
                    connection.close();
                }
            }catch(SQLException exception){
                Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
            }
        }
    }
}