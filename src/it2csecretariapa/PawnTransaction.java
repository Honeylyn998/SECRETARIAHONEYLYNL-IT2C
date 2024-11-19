package it2csecretariapa;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class PawnTransaction {
    public void ptransaction (){ 
    
     Scanner sc = new Scanner(System.in);
     String response;
     do{
          System.out.println("PAWNTRANSACTION PANEL");
            
            System.out.println("1. ADD PAWNTRANSACTION");
            System.out.println("2. VIEW PAWNTRANSACTION ");
            System.out.println("3. UPDATE PAWNTRANSACTION");      
            System.out.println("4. DELETE PAWNTRANSACTION");
             System.out.println("5. EXIT");
            System.out.println("Enter action");           
            int  act = sc.nextInt();
           PawnTransaction pt = new PawnTransaction();
           Customer cs = new Customer();
           ItemPawn ip = new ItemPawn();
            switch(act){
               
                case 1:     cs.viewCustomer();    
                     pt.addPawnTransaction();
                     pt.viewPawnTransaction();
               break;
                
                case 2:            
                    pt.viewPawnTransaction();
                                         
                break;
                
                case 3:    ip.viewItemPawn();
                    pt.updatePawnTransaction();
                    pt.viewPawnTransaction();
            
                break;
                
                case 4: pt.viewPawnTransaction();
                    pt.deletePawnTransaction();
                    pt.viewPawnTransaction();
                break;
                
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    
                   System.out.println("Invalid Selection, Please Try Again. ");
                 }
            
            System.out.println("Continue? (yes/No)");
                response = sc.next();
 
           }while(response.equalsIgnoreCase("yes"));
             
    }
 
    private void addPawnTransaction(){
        Config conf = new Config();
      Scanner sc = new Scanner(System.in);
        Customer cs = new Customer();
        
       System.out.print("SELECT ID OF THE SELECTED CUSTOMER: ");
        int c_id = sc.nextInt();
        
        String sql= "SELECT c_id FROM customer WHERE c_id = ?";
        while(conf.getSingleValue(sql,c_id)  == 0){
            System.out.print("SELECTED ID DOESNT EXIST PLEASE TRY AGAIN");
            c_id = sc.nextInt();         
}
        ItemPawn ip = new ItemPawn();
        ip.viewItemPawn();
        System.out.println("Enter ID of Item");
        int iid = sc.nextInt();
        
        String pslq = "SELECT i_id FROM itempawn WHERE i_id = ?";
        while(conf.getSingleValue(pslq, iid) == 0){
            System.out.println("ITEMPAWN DOES NOT EXIST SELECT AGAIN.");
            iid = sc.nextInt();
            
     
        }
        System.out.println("Enter Quantity");
        double quantity = sc.nextDouble();
        
        
        String amountqry = "SELECT i_amount FROM itempawn WHERE i_id = ?";
        double amount = conf.getSingleValue(amountqry, iid);
        double due = amount * quantity;
        
        System.out.println(".......................................................");
        
        System.out.println("TOtal DUE:"+due);
        
        System.out.println(".......................................................");

        System.out.println("");
        
        
       System.out.println("Enter Amount Received:");
double ramount = sc.nextDouble();


while (ramount < due) {
    System.out.println("Invalid amount, TRY AGAIN!!");
    ramount = sc.nextDouble();
}

System.out.println("Cash Received: " + ramount);
double change = ramount - due;
System.out.println("Change: " + change);

LocalDate currdate = LocalDate.now();
DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd");
String date = currdate.format(format);

String pawntransactionqry = "INSERT INTO pawntransaction (c_id, i_id, p_quantity, p_due, p_ramount, p_change, p_date)"
        + " VALUES (?, ?, ?, ?, ?, ?, ?)";

conf.addRecord(pawntransactionqry, c_id, iid, quantity, due, ramount, change, date);
}
     public void viewPawnTransaction() {
    String qry = "SELECT p_id, c_lname, i_iname, p_due, p_ramount, p_change, p_date FROM pawntransaction"
               + " LEFT JOIN customer ON customer.c_id = pawntransaction.c_id"
               + " LEFT JOIN itempawn ON itempawn.i_id = pawntransaction.i_id";
    
    String[] hrds = {"PawnTransaction ID", "Customer", "ItemPawn", "Total", "Amount Received", "Change", " Renewal Date"};
    String[] clmns = {"p_id", "c_lname", "i_iname", "p_due", "p_ramount", "p_change", "p_date"};
    
    Config conf = new Config();
    conf.viewRecord(qry, hrds, clmns);

                
}
        private void updatePawnTransaction() {
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter PawnTransaction ID to Update: ");
        int p_id = sc.nextInt();
        
        while (conf.getSingleValue("SELECT p_id FROM pawntransaction WHERE p_id = ?", p_id) == 0) {
            System.out.print("PawnTransaction ID does not exist. Try Again: ");
            p_id = sc.nextInt();
        }
        
        System.out.print("Enter New Quantity: ");
        double quantity = sc.nextDouble();
        
        String amountqry = "SELECT i_amount FROM itempawn WHERE i_id = (SELECT i_id FROM pawntransaction WHERE t_id = ?)";
        double amount = conf.getSingleValue(amountqry, p_id);
        double due = amount * quantity;
        
        System.out.println("New Total Due: " + due);
        
        System.out.print("Enter New Amount Received: ");
        double ramount = sc.nextDouble();
        
        while (ramount < due) {
            System.out.print("Invalid Amount. Try again: ");
            ramount = sc.nextDouble();
        }
        String updateqry = "UPDATE pawntransaction SET p_quantity = ?, p_due = ?, p_ramount = ? WHERE p_id = ?";
        conf.updateRecord(updateqry, quantity, due, ramount, p_id);
        
        System.out.println("PawnTransaction Updated Successfully!");
    }
    
    private void deletePawnTransaction() {
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Enter PawnTransaction ID to delete: ");
        int p_id = sc.nextInt();
        
        while (conf.getSingleValue("SELECT p_id FROM pawntransaction WHERE p_id = ?", p_id) == 0) {
            System.out.print("PawnTransaction ID Does Not Exist. Try Again: ");
            p_id = sc.nextInt();
        }
        
        String deleteqry = "DELETE FROM pawntransaction WHERE p_id = ?";
        conf.deleteRecord(deleteqry, p_id);
        
        System.out.println("PawnTransaction Deleted Successfully!");
    }
}