/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
/**
 *
 * @author Ricky
 */


public class Database {

private Connection con;

public void connect() throws Exception{

    if(con != null) return;

    try {
        Class.forName("com.mysql.jdbc.Driver");
    } catch (ClassNotFoundException e) {
        throw new Exception("No database");
    }

    String connectionURL = "jdbc:mysql://localhost:3306/billpay?verifyServerCertificate=false&useSSL=true";

    con = DriverManager.getConnection(connectionURL, "root", "admin");        
}

public Statement createStatement() throws Exception{
    return con.createStatement();
}

public PreparedStatement prepareStatement(String query) throws Exception{
    return con.prepareStatement(query);
}
public void close(){
    if(con != null){
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
}
