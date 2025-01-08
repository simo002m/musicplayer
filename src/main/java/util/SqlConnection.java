package util;

import java.sql.*;

public class SqlConnection {

    private static final String URL = "jdbc:sqlserver://162.19.246.106:1433;databaseName=YoussefDB;";
    private static final String USERNAME = "youssefgruppen";
    private static final String PASSWORD = "youssef123!";

    public static Connection getConnection() throws Exception
    {
        Connection con = null;
        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        return con;
    }
}
