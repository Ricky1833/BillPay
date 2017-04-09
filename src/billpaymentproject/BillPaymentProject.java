/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package billpaymentproject;
import java.sql.*;
/**
 *
 * @author Ricky
 */
public class BillPaymentProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException{
        
        Database db = new Database();
        //Statement stmt=null;
        //ResultSet rset=null;
       try {
         db.connect();
         //stmt=db.createStatement();
         /*
         rset=stmt.executeQuery("select * from payee_type");
         while (rset.next()){
          PayeeType payeeType= new PayeeType();
          payeeType.retrieve(rset);
          
          String text=String.valueOf(payeeType.getSerial());
          text+=",";
          text+=payeeType.getCode();
          System.out.println(text);
         }
         */
         Payment payment=new Payment(db,
                                      "Test Payment",
                                      0,
                                      100.23,
                                       "Amex");
         payment.insert(db);
        } 
        catch (Exception e) {
            e.printStackTrace();
        }                                                                                                            
        finally {
            db.close();
        }
       
    }
    
}
