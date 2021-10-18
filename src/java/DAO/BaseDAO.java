/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author My Computer
 */
public class BaseDAO {
    protected Connection connection;
    private final String username = "sa";
    private final String password = "dinhcongthanh1";
    private final String url = "jdbc:sqlserver://LAPTOP-J7TMDJ5A\\SQLEXPRESS:1433;databaseName=QuangTrungHighschool";
    
    public BaseDAO(){
        connection = getConnection();
    }
    
    protected Connection getConnection(){
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            return DriverManager.getConnection(url,username,password);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(BaseDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
}
