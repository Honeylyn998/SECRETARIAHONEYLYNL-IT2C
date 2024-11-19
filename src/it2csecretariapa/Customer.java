package it2csecretariapa;
import java.util.Scanner;
 
public class Customer {
    public void cTransaction(){
        
        Scanner sc = new Scanner(System.in);
        String response;
        do{
            
 
        System.out.println("------------------------------");
        System.out.println("CUSTOMER PANEL");
        System.out.println("1. ADD CUSTOMER");
        System.out.println("2. VIEW CUSTOMER ");
        System.out.println("3. UPDATE CUSTOMER ");
        System.out.println("4. DELETE CUSTOMER ");
        System.out.println("5. EXIT");
        
        System.out.println("ENTER SELECTION");
        int act = sc.nextInt();
        Customer cs = new Customer();
        switch(act){
            
           case 1: 
               cs.addCustomer();
               cs.viewCustomer();
                break;
                
           case 2:
               cs.viewCustomer();
               
               break;
           case 3:
               cs.updateCustomer();
               break;
               
           case 4:
               cs.DeleteCustomer();
               break;
               
           case 5:
                System.out.println("Exiting...");
                break;
                default:
                System.out.println("Invalid action. Please try again.");        
       }
        System.out.println("Do You Want to Continue? (yes/no): ");
        response = sc.next();
    
    }while(response.equalsIgnoreCase("yes"));
    
}
    
 public void addCustomer(){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Customer FirstName: ");
        String c_fname = sc.next();
        
        System.out.print("Customer LastName: ");
        String c_lname = sc.next();
        
        System.out.print("Customer Address: ");
        String c_address = sc.next();
        
        
        System.out.print("Customer Phonenum: ");
        String c_phonenum = sc.next();
       

  String sql = "INSERT INTO customer ( c_fname, c_lname, c_address, c_phonenum) VALUES ( ?, ?, ?, ?)";
       Config conf =  new Config();
        conf.addRecord(sql, c_fname, c_lname, c_address, c_phonenum );
    }
     public void viewCustomer(){
   
        String cqry = "SELECT * FROM customer";
        String[] hrds = {"ID", "FirstName", "LastName", "Address", "Phonenum"};
        String[] clmns = {"c_id", "c_fname", "c_lname", "c_address", "c_phonenum"};
        Config conf =  new Config();
        conf.viewRecord(cqry, hrds, clmns);
    }

     
     private void updateCustomer(){
         Scanner sc = new Scanner(System.in);
         System.out.print("Enter Customer ID:");
         int c_id = sc.nextInt();
         
         System.out.println("Enter New Customer FirstName:");
         String c_fname = sc.next();
         
         System.out.println("Enter New Customer LastName:");
         String c_lname = sc.next();
         
         System.out.println("Enter New Customer Address:");
         String c_address = sc.next();
         
         System.out.println("Enter New  Customer Phonenum:");
         String c_phonenum = sc.next();
         String qry = "UPDATE customer SET c_fname = ?, c_lname = ?, c_address = ?, c_phonenum = ? WHERE c_id = ?";
         Config conf =  new Config();
         conf.updateRecord(qry, c_fname, c_lname, c_address, c_phonenum ,c_id );
      }

     
      public void DeleteCustomer(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Customer ID to Delete: ");
        int id = sc.nextInt();
        
        
         String sqlDelete = "DELETE FROM customer WHERE c_id = ?";
        Config conf =  new Config();
         conf.deleteRecord(sqlDelete, id);
       

}
}
