package top.novashen.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class C3P0Utils {

    private static ComboPooledDataSource comboPooledDataSource = null;

    static {
        try {

            comboPooledDataSource = new ComboPooledDataSource();
//            comboPooledDataSource.setDriverClass();
//            comboPooledDataSource.setUser();
//            comboPooledDataSource.setPassword();
//            ......


//            comboPooledDataSource = new ComboPooledDataSource("mysql...");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static Connection getConnection() throws SQLException {
        return comboPooledDataSource.getConnection();
    }

    public static void close(Connection connection, Statement statement, ResultSet resultSet) throws SQLException {
        if (resultSet != null) {
            resultSet.close();
        }
        if (statement != null) {
            statement.close();
        }
        if (connection != null) {
            connection.close();
        }
    }


}
