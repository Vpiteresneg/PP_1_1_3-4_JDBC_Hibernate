package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String PASSWORD = "89928992";
    private static final String USERNAME = "root";
    private static final String URL = "jdbc:mysql://localhost:3306/555";
    private static final String DRIVER = "com.mysql.jdbc.Driver";

    private  Util() {

    }
    //открывает соединение и возвращает его
    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(URL, USERNAME, PASSWORD);//
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
