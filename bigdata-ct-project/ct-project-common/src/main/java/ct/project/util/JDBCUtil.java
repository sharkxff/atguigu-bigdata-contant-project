package ct.project.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * @author zxfcode
 * @create 2018-11-09 11:14
 */
public class JDBCUtil  {
    private static final String MYSQL_DRIVER_CLASS = "com.mysql.jdbc.Driver";
    private static final String MYSQL_URL = "jdbc:mysql://hadoop102:3306/ct?useUnicode=true&characterEncoding=UTF-8";
    private static final String MYSQL_USERNAME = "root";
    private static final String MYSQL_PASSWORD = "000000";

    public static Connection getConnection(){
        Connection conn = null;
        try {
            Class.forName(MYSQL_DRIVER_CLASS);
            conn= DriverManager.getConnection(MYSQL_URL,MYSQL_USERNAME,MYSQL_PASSWORD);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
