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
public class Payee {
    private int serial;
    private String name;
    private String description;
    private int payee_type_serial;
    
    Payee(){
        name=null;
        description=null;
    }
    
    Payee(String name,
          String description,
          int payee_type_serial){
        this.name=name;
        this.description=description;
        this.payee_type_serial=payee_type_serial;
    }
    
    Payee(Database db,
          String name,
          String description,
          String payee_type_code){
        this.name=name;
        this.description=description;
        try{
            int tmp=findPayeeTypeSerial(db,payee_type_code);
            if (tmp==-1)
              tmp=findPayeeTypeSerial(db,"OT"); 
            this.payee_type_serial=tmp;
        }
        catch (Exception e) {
            e.printStackTrace();
        } 
        db.close();
    }
    
    public void retrieve(ResultSet rset) throws Exception{
        this.serial=rset.getInt("serial");
        this.name=rset.getString("name");
        this.description=rset.getString("description");
        this.payee_type_serial=rset.getInt("payee_type_serial");
    }
    
    public void insert(Database db) throws Exception{
      Statement stmt=db.createStatement();
      String sqlText="INSERT INTO payee(";
      if  (!this.name.isEmpty())
          sqlText=sqlText +"name,";
      if  (!this.description.isEmpty())
          sqlText=sqlText +"description,";
      if (this.payee_type_serial!=0)
          sqlText=sqlText+"payee_type_serial";
      sqlText=sqlText+") VALUES (";
      if  (!this.name.isEmpty()){
          sqlText=sqlText+"'";
          sqlText=sqlText +this.name;
          sqlText=sqlText+"',";
      }
      if  (!this.description.isEmpty()){
          sqlText=sqlText+"'";
          sqlText=sqlText +this.description;
          sqlText=sqlText+"',";
      }
      if (this.payee_type_serial!=0)
          sqlText=sqlText+Integer.toString(this.payee_type_serial);
      sqlText=sqlText+")";
      stmt.executeUpdate(sqlText);
      //System.out.println(sqlText);
      stmt.close();
    }
    public String getName(){
        return this.name;
    }
    
    public void setName(String name){
        this.name=name;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public int getSerial(){
        return this.serial;
    }
    
    public int getPayeeTypeSerial(){
        return this.payee_type_serial;
    }
    
    public void setPayeeTypeSerial(Database db,String code) throws Exception{
        int serial=findPayeeTypeSerial(db,code);
        this.payee_type_serial=serial;
    }
    
    public int findPayeeTypeSerial(Database db,String code) throws Exception{
        //db.connect();
        int serial;
        Statement stmt=db.createStatement();
        ResultSet rset=stmt.executeQuery("select serial from payee_type where code='"+code+"'");
        if (rset.next()){
         serial=rset.getInt("serial");
        }
        else
         serial=-1;
         return serial;
    }
}
