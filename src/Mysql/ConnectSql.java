/**
 * @name: ConnectSql
 * @author: Nguyen Dao Hoai Thuan
 * @version: 1.0
 * @created: 4/24/2025
 */

package Mysql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectSql {
    private static ConnectSql instance = new ConnectSql();
    private Connection connection = null;

    public static ConnectSql getInstance() {
        return instance;
    }

    public static Connection getConnection() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLCF;encrypt=false;";
        String user = "sa";
        String password = "YourStrong@Passw0rd";

        try {
            return DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


    private ConnectSql() {
        String url = "jdbc:sqlserver://localhost:1433;databaseName=QLCF;encrypt=false;";
        String user = "sa";
        String password = "YourStrong@Passw0rd"; // thay bằng mật khẩu thật của bạn

        try {
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Kết nối thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Kết nối thất bại!");
        }
    }
}