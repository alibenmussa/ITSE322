/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package itse322;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author ahmed
 */
public class DB {
    private static Connection connection = null;
    
    public static Connection DBconnect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/municipality", "root", "");
            return connection;

        } catch (ClassNotFoundException | SQLException ex) {
            Alert.viewErrorMessage("لا يمكن الاتصال بقاعدة البيانات");
            return connection;
        }
    }
}
