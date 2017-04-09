/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;

import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author Ricky
 */
public class PayeeType {
    
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
    
    
}
