package demo.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conecao {
    private static final String DRIVER = "com.mysql.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/coursejdbc?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "27m12l93r";
    private static Connection conn = null;
    
    public static Connection getConnection() {
        if (conn == null) {
        try {
            Class.forName(DRIVER);
            conn = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            System.out.println("Erro de conexão" + ex);
        }   catch (ClassNotFoundException ex) {
                Logger.getLogger(Conecao.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return conn;
    }
    
    public static void closeConnection(){
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException ex) {
               System.err.println("Erro: " + ex);
            }
        }
    }   
    public static void closeConnection(Connection con, Statement stnt){
        if (stnt != null) {
            try {
                stnt.close();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
                }
        }
          closeConnection();
    }
    public static void closeConnection(Connection con, Statement stnt,ResultSet rn){
        if (rn != null) {
            try {
                rn.close();
            } catch (SQLException ex) {
                System.err.println("Erro: " + ex);
                }
        }
        closeConnection(con,stnt);
    }  
}