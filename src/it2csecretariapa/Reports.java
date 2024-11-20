package it2csecretariapa;

import java.util.Scanner;

public class Reports {

    
    public void generateGeneralReport() {
          Scanner sc = new Scanner(System.in);
        Config conf = new Config();

             String qry = "SELECT "
                   + "SUM(p_due) AS TotalDue, "
                   + "SUM(p_ramount) AS TotalReceived, "
                   + "SUM(p_change) AS TotalChange "
                   + "FROM pawntransaction";

        System.out.println("General Report: Summary of Transactions");
        String[] hrds = {"Total Due", "Total Received", "Total Change"};
        String[] clmns = {"TotalDue", "TotalReceived", "TotalChange"};
        conf.viewRecord(qry, hrds, clmns);
    }

    
    public void generateCustomerReport() {
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Customer ID: ");
        int c_id = sc.nextInt();

       
        String qry = "SELECT p_id, c_lname, i_iname, p_quantity, p_due, p_ramount, p_change, p_date "
                   + "FROM pawntransaction "
                   + "LEFT JOIN customer ON customer.c_id = pawntransaction.c_id "
                   + "LEFT JOIN itempawn ON itempawn.i_id = pawntransaction.i_id "
                   + "WHERE pawntransaction.c_id = ?";

        System.out.println("Specific Report: Transactions for Customer ID " + c_id);
        String[] hrds = {"PawnTransaction ID", "Customer", "ItemPawn", "Quantity", "Due", "Received", "Change", "Date"};
        String[] clmns = {"p_id", "c_lname", "i_iname", "p_quantity", "p_due", "p_ramount", "p_change", "p_date"};
        conf.viewRecord(qry, hrds, clmns, c_id);
    }

   
    public void generateItemPawnedReport() {
        Config conf = new Config();
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Item ID: ");
        int i_id = sc.nextInt();

        
        String qry = "SELECT p_id, c_lname, i_iname, p_quantity, p_due, p_ramount, p_change, p_date "
                   + "FROM pawntransaction "
                   + "LEFT JOIN customer ON customer.c_id = pawntransaction.c_id "
                   + "LEFT JOIN itempawn ON itempawn.i_id = pawntransaction.i_id "
                   + "WHERE pawntransaction.p_id = ?";

        System.out.println("Specific Report: Details for Item ID " + i_id);
        String[] hrds = {"PawnTransaction ID", "Customer", "ItemPawn", "Quantity", "Due", "Received", "Change", "Date"};
        String[] clmns = {"p_id", "c_lname", "i_iname", "p_quantity", "p_due", "p_ramount", "p_change", "p_date"};
        conf.viewRecord(qry, hrds, clmns, i_id);
    }

    
    public void reportMenu() {
        Scanner sc = new Scanner(System.in);
        String response;

        do {
            System.out.println("REPORT GENERATION PANEL");
            System.out.println("1. Generate General Report");
            System.out.println("2. Generate Report for a Specific Customer");
            System.out.println("3. Generate Report for a Specific Item");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            PawnTransaction pt = new PawnTransaction();
             ItemPawn ip = new ItemPawn();

            Customer cs = new Customer();
            switch (choice) {
                case 1:
                    generateGeneralReport();
                    break;
                case 2:
                      cs.viewCustomer();
                    generateCustomerReport();
                    break;
                case 3:     ip.viewItemPawn();
                           generateItemPawnedReport();
                    break;
                case 4:
                    System.out.println("Exiting Report Generation Panel...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.println("Do you want to generate another report? (yes/no)");
            response = sc.next();
        } while (response.equalsIgnoreCase("yes"));
    }
} 
