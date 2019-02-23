package itse322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * مشروع مادة جافا المتقدمة
 * 
 * 216180392 - يوسف عبد الكريم بريكة
 * 216180296 - علي جمال الدين بن موسى
 * 
 */


public class DB {
    private static Connection connection = null;
    
    final static String db = "jdbc:mysql://localhost/municipality?useUnicode=yes&characterEncoding=UTF-8";
    final static String username = "root";
    final static String password = "";
    
    
    public static Connection DBconnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(db, username, password);
            return connection;

        } catch (ClassNotFoundException | SQLException ex) {
            Message.showError("لا يمكن الاتصال بقاعدة البيانات");
            return connection;
        }
    }
}