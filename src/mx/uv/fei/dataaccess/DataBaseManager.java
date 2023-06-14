package mx.uv.fei.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
import java.util.logging.Level;

public class DataBaseManager {
    private Connection connection;
    private Properties dataBaseUserPropertiesFile;

    public DataBaseManager(){
        try {
            InputStream fis = getClass().getResourceAsStream("/dependencies/resources/DatabaseAccess.properties");
            dataBaseUserPropertiesFile = new Properties();
            dataBaseUserPropertiesFile.load(fis);
        } catch (IOException e) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, e);
        }
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

    private void connect() throws SQLException{
        connection = DriverManager.getConnection(
            dataBaseUserPropertiesFile.getProperty("DATABASE_NAME"),
            dataBaseUserPropertiesFile.getProperty("DATABASE_USER"),
            dataBaseUserPropertiesFile.getProperty("DATABASE_PASSWORD")
        );
    }
}