package top.novashen.jdbc01;

import top.novashen.utils.Utils;

import java.sql.*;

public class JDBCHelloWorld {
    public static void main(String[] args) throws ClassNotFoundException, SQLException {



//        //加载jdbc
//        Class.forName("com.mysql.jdbc.Driver");
//
//        //连接数据库
//
//        String url = "jdbc:mysql://localhost:3306/jdbcstudy?useUnicode=true&characterEncoding=utf8&useSSL=true";
//        String userName = "root";
//        String passWord = "123456";
//        //获取一个连接
//        Connection connection = DriverManager.getConnection(url, userName, passWord);

//        使用工具类获取连接测试
        Connection connection = Utils.getConnection();


        //获取这个连接的对象，执行SQL的对象
        Statement statement = connection.createStatement();
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM users WHERE id=?;");


        //执行sql

        String sql = "SELECT * FROM users;";
        //执行，并返回结果集，是个列表
        ResultSet resultSet = statement.executeQuery(sql);

        while (resultSet.next()) {
            System.out.println("id " + resultSet.getObject("id"));
            System.out.println("name " + resultSet.getObject("NAME"));
            System.out.println("pwd " + resultSet.getObject("PASSWORD"));
            System.out.println("email " + resultSet.getObject("email"));
            System.out.println("birth " + resultSet.getObject("birthday"));
        }


        //关闭连接

        resultSet.close();
        statement.close();
        preparedStatement.close();
        connection.close();




    }
}
