package top.novashen.jdbc02Transaction;

import top.novashen.utils.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Transaction {
    public static void main(String[] args) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;

        connection = Utils.getConnection();
        //关闭数据库的自动提交==开启事务
        connection.setAutoCommit(false);

//        try {
//            //模拟执行成功
//            String sql1 = "update users set `PASSWORD`='asd' where id=2";
//            statement = connection.prepareStatement(sql1);
//            statement.executeUpdate();
//
//            String sql2 = "update users set `PASSWORD`='assssd' where id=2";
//            statement = connection.prepareStatement(sql2);
//            statement.executeUpdate();
//
//            //提交事务
//            connection.commit();
//            System.out.println("Successful!");
//        } catch (SQLException e) {
//            connection.rollback();
//        }

        try {
            //模拟执行失败
            String sql1 = "update users set `PASSWORD`='asd' where id=2";
            statement = connection.prepareStatement(sql1);
            statement.executeUpdate();

            int x=1/0;

            String sql2 = "update users set `PASSWORD`='assssd' where id=2";
            statement = connection.prepareStatement(sql2);
            statement.executeUpdate();

            //提交事务
            connection.commit();
            System.out.println("Successful!");
        } catch (SQLException e) {
            connection.rollback();
        }

        connection.setAutoCommit(true);
        Utils.close(connection,statement,null);
    }
}
