/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Ricky
 */
public class PayeeType {

    boolean isValid(Database db) {
        boolean valid=true;
        String query="Select * From payee_type where description='"+this.description
                +"' or code='"+this.code+"'";
        try{
        PreparedStatement stmt=db.prepareStatement(query);
        ResultSet rset=stmt.executeQuery(query);
        if  (rset.next()) { 
          valid=false;
        } 
        rset.close(); 
        stmt.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return valid;
    }
    
    private int serial;
    private String code;
    private String description;
    
    PayeeType(){
        code=null;
        description=null;
    }
    
    PayeeType( String code,
               String description){
        this.code=code;
        this.description=description;
    }
    
    public void insert(Database db) throws Exception{
      Statement stmt=db.createStatement();
      String sqlText="INSERT INTO payee_type(";
      if  (!this.code.isEmpty())
          sqlText=sqlText +"code,";
      if  (!this.description.isEmpty())
          sqlText=sqlText +"description";
      sqlText=sqlText+") VALUES (";
      if  (!this.code.isEmpty()){
          sqlText=sqlText+"'";
          sqlText=sqlText +this.code;
          sqlText=sqlText+"',";
      }
      if  (!this.description.isEmpty()){
          sqlText=sqlText+"'";
          sqlText=sqlText +this.description;
          sqlText=sqlText+"'";
      }
      sqlText=sqlText+")";
      stmt.executeUpdate(sqlText);
      stmt.close();
    }
     
    public void retrieve(ResultSet rset) throws Exception{
        this.serial=rset.getInt("serial");
        this.code=rset.getString("code");
        this.description=rset.getString("description");
        rset.close();
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public String getCode(){
        return this.code;
    }
    
    public void setCode(String code){
        this.code=code;
    }
    
    public int getSerial(){
        return this.serial;
    }
    
     
    public static ArrayList<String> getPayeeTypeArray(Database db) {
        ArrayList<String> list = new ArrayList<>();
        String query="SELECT description FROM payee_type ORDER BY description";
        try{
        PreparedStatement stmt=db.prepareStatement(query);
        ResultSet rset=stmt.executeQuery(query);
        while (rset.next()) { 
            String payeeTypeDesc = rset.getString("description"); 
            // add group names to the array list
            if (!payeeTypeDesc.isEmpty())
             list.add(payeeTypeDesc);
        } 
        stmt.close();
        rset.close(); 
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
