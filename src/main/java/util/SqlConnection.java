package util;

import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:sqlserver://localhost;portNumber=1433;databaseName=musicplayer";
    //private static final String URL = "jdbc:sqlserver://162.19.246.106:1433;databaseName=musicplayer;";
    private static final String USERNAME = "sa"; // replace with your username
    private static final String PASSWORD = "1234"; // replace with your password

    public static Connection getConnection() throws Exception
    {
        Connection con = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }
}
