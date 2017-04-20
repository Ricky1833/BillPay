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
        rset.close();
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
    
    public void setPayeeTypeSerial(int serial){
        this.payee_type_serial=serial;
    }
    
    public void setPayeeTypeSerial(Database db,String code) throws Exception{
        int serial=findPayeeTypeSerial(db,code);
        this.payee_type_serial=serial;
    }
    
    public static int findPayeeTypeSerial(Database db,String code) throws Exception{
        //db.connect();
        int typeSerial;
        Statement stmt=db.createStatement();
        ResultSet rset=stmt.executeQuery("select serial from payee_type where code='"+code+"'");
        if (rset.next()){
         typeSerial=rset.getInt("serial");
        }
        else
         typeSerial=-1;
        stmt.close();
        rset.close();
         return typeSerial;
    }
    
    
    public int findPayeeTypeSerialByDesc(Database db,String desc) throws Exception{
        //db.connect();
        int serial;
        Statement stmt=db.createStatement();
        ResultSet rset=stmt.executeQuery("select serial from payee_type where description='"+desc+"'");
        if (rset.next()){
         serial=rset.getInt("serial");
        }
        else
         serial=-1;
        stmt.close();
        rset.close();
         return serial;
    }
    
    
    
    public static ArrayList<String> getPayeeArray(Database db) {
        ArrayList<String> list = new ArrayList<>();
        String query="SELECT name FROM payee ORDER BY name";
        try{
        PreparedStatement stmt=db.prepareStatement(query);
        ResultSet rset=stmt.executeQuery(query);
        while (rset.next()) { 
            String payeeName = rset.getString("name"); 
            // add group names to the array list
            if (!payeeName.isEmpty())
             list.add(payeeName);
        } 
        stmt.close();
        rset.close(); 
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return list;
    }
    
   public static String getPayeeName(Database db,int serial) {
    String query="Select name from payee where serial="+serial; 
    String payeeName=null;
    try{
        PreparedStatement stmt=db.prepareStatement(query);
        ResultSet rset=stmt.executeQuery(query);
        if (rset.next()){
            payeeName=rset.getString("name");
        }
        rset.close();
        stmt.close();
    }
    catch(Exception e){
        e.printStackTrace();
    }
    return payeeName;
   }

    boolean isValid(Database db) {
       boolean valid=true;
        String query="Select * From payee where name='"+this.name+"'";
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
}
