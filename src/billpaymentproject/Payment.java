/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;
import java.util.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.ZoneId;
/**
 *
 * @author Ricky
 */
public class Payment {
    private int serial;
    private double amount;
    private String description;
    private int status;
    private java.sql.Date payment_date;
    private int payee_serial;
    
    Payment(){
        amount=0.00;
        description=null;
        status=0;
        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "America/Los_Angeles" ) );
        payment_date=java.sql.Date.valueOf( todayLocalDate );
    }
    
    Payment(String description,
            int status,
            double amount,
            int payee_serial){
        this.amount=amount;
        this.description=description;
        this.status=status;
        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "America/Los_Angeles" ) );
        this.payment_date=java.sql.Date.valueOf( todayLocalDate );
        this.payee_serial=payee_serial;
    }
    
    Payment(String description,
            java.sql.Date payment_date,
            int status,
            double amount,
            int payee_serial){
        this.amount=amount;
        this.description=description;
        this.status=status;
        this.payment_date=payment_date;
        this.payee_serial=payee_serial;
    }
    
    Payment(Database db,
            String description,
            int status,
            double amount,
            String payee_name){
        this.amount=amount;
        this.description=description;
        this.status=status;
        LocalDate todayLocalDate = LocalDate.now( ZoneId.of( "America/Los_Angeles" ) );
        this.payment_date=java.sql.Date.valueOf( todayLocalDate );
        try{
            int tmp=findOrAddPayeeSerial(db,payee_name);
            if (tmp!=-1)
                this.payee_serial=tmp;
        }
        catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    Payment(Database db,
            String description,
            java.sql.Date payment_date,
            int status,
            double amount,
            String payee_name){
        this.amount=amount;
        this.description=description;
        this.status=status;
        this.payment_date=payment_date;
        try{
            int tmp=findOrAddPayeeSerial(db,payee_name);
            if (tmp!=-1)
                this.payee_serial=tmp;
        }
        catch(Exception e){
            e.printStackTrace();
        }
            
    }
    
    public void retrieve(ResultSet rset) throws Exception{
        this.serial=rset.getInt("serial");
        this.description=rset.getString("description");
        this.payment_date=rset.getDate("payment_date");
        this.status=rset.getInt("status");
        this.amount=rset.getDouble("amount");
        this.payee_serial=rset.getInt("payee_serial");
    }

    public void insert(Database db) throws Exception{
      Statement stmt=db.createStatement();
      String sqlText="INSERT INTO payment(";
      if  (!this.description.isEmpty())
          sqlText=sqlText +"description,";
      if (this.payment_date!=null)
          sqlText=sqlText+"payment_date,";
      
      sqlText=sqlText+"status,";
      sqlText=sqlText+"amount,";
      if (this.payee_serial!=0)
          sqlText=sqlText+"payee_serial";
      sqlText=sqlText+") VALUES(";
      if  (!this.description.isEmpty()){
          sqlText=sqlText+"'";
          sqlText=sqlText +this.description;
          sqlText=sqlText+"',";
      }
      if (this.payment_date!=null){
          sqlText=sqlText+"'";
          sqlText=sqlText+this.payment_date.toString();
          sqlText=sqlText+"',";
      }
      sqlText=sqlText+Integer.toString(this.status);
      sqlText=sqlText+",";
      sqlText=sqlText+Double.toString(this.amount);
      sqlText=sqlText+",";
      if (this.payee_serial!=0)
          sqlText=sqlText+Integer.toString(this.payee_serial);
      sqlText=sqlText+")";
      stmt.executeUpdate(sqlText);
      stmt.close();
    }
    
    public int getPayeeSerial(){
        return this.payee_serial;
    }
    
    //make this private and make a public version based on find method in sql
    public void setPayeeSerial(int serial){
        this.payee_serial=serial;
    }
    
    public Date getDate(){
        return this.payment_date;
    }
    
    public void setDate(java.sql.Date newDate){
        this.payment_date=newDate;
    }
    
    public String getDescription(){
        return this.description;
    }
    
    public void setDescription(String description){
        this.description=description;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public void setAmount(double amount){
        this.amount=amount;
    }
    
    public int getStatus(){
        return this.status;
    }
    
    public void setStatus(int status){
        this.status=status;
    }
    
    public int getSerial(){
        return this.serial;
    }
    
    public int findOrAddPayeeSerial(Database db,String name) throws Exception{
        //db.connect();
        int serial;
        Statement stmt=db.createStatement();
        ResultSet rset=stmt.executeQuery("select serial from payee where name='"+name+"'");
        if (rset.next()){
         serial= rset.getInt("serial");
        }
        else {
         Payee payee=new Payee(db,name,"None","OT"); 
         payee.insert(db);
         stmt=db.createStatement();
         rset=stmt.executeQuery("select serial from payee where name='"+name+"'");
         if (rset.next()){
            serial= rset.getInt("serial");
         }
         else
             serial=-1;
        }
         return serial;
    }
}
