package mx.uv.fei;

import java.sql.SQLException;

import mx.uv.fei.dataaccess.DataBaseManager;


public class Main {
    public static void main (String arg[]) throws SQLException {
        DataBaseManager dbm = new DataBaseManager();
        dbm.getConnection();
        //System.out.println(path.getFileName());
        //System.out.println(path.toAbsolutePath());
    }
}
