package mx.uv.fei.dataaccess;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Properties;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;


import com.gluonhq.impl.charm.a.b.b.e;
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

    private SecretKeySpec makeKey(String password){
        
        try {
            byte[] chain = password.getBytes("UTF-8");
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
            chain = messageDigest.digest(chain);
            chain = Arrays.copyOf(chain, 16);
            SecretKeySpec secretKeySpec = new SecretKeySpec(chain, "AES");
            return secretKeySpec;
        }catch(UnsupportedEncodingException exception) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
        }catch(NoSuchAlgorithmException exception) {
            Logger.getLogger(DataBaseManager.class.getName()).log(Level.SEVERE, null, exception);
        }
        return null;
    }

    private String desencodeDatabasePassword(String decode){        
        SecretKeySpec secretKeySpec = makeKey();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

        byte[] chain = Base.decode(decode);
        byte[] decoding = cipher.doFinal(chain);
        String decodedPassword = new String(decoding);

        return decodedPassword;
    }
}