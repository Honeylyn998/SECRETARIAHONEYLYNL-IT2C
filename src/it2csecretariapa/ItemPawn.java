package it2csecretariapa;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ItemPawn {
    Scanner sc = new Scanner(System.in);
    String response;

    public void Itransaction() {
        do {
            System.out.println("------------------------------");
            System.out.println("ITEMPAWN PANEL");
            System.out.println("1. ADD ItemPawn");
            System.out.println("2. VIEW ItemPawn");
            System.out.println("3. UPDATE ItemPawn");
            System.out.println("4. DELETE ItemPawn");
            System.out.println("5. EXIT");
            System.out.println("ENTER SELECTION");
            
            int act = sc.nextInt();
            sc.nextLine();  
            switch (act) {
                case 1:
                    addItemPawn();
                    viewItemPawn();
                    break;
                case 2:
                    viewItemPawn();
                    break;
                case 3:viewItemPawn();
                    updateItemPawn();
                    viewItemPawn();
                    break;
                case 4:viewItemPawn();
                    deleteItemPawn();
                    viewItemPawn();
                    break;
                case 5:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid action. Please try again.");
            }

            System.out.println("Do You Want to Continue? (yes/no): ");
            response = sc.nextLine();

        } while (response.equalsIgnoreCase("yes"));
    }

    public void addItemPawn() {
        System.out.print("ItemPawn Item Name: ");
        String iname = sc.nextLine();

        System.out.print("ItemPawn Description: ");
        String idescription = sc.nextLine();

        Double iquantity = getValidDoubleInput("ItemPawn Quantity: ");
        Double iamount = getValidDoubleInput("ItemPawn Amount: ");

        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd");
        String date = currdate.format(format);

        String sql = "INSERT INTO itempawn (i_iname, i_description, i_quantity, i_amount , i_date) VALUES (?, ?, ?, ?, ?)";
        Config conf = new Config();
        conf.addRecord(sql, iname, idescription, iquantity, iamount, date);
    }

    public void viewItemPawn() {
        String cqry = "SELECT i_id, i_iname, i_description, i_quantity, i_amount, i_date FROM itempawn";
        String[] hrds = {"ID", "Item Name", "Description", "Quantity", "Amount", "Item Pawned Date"};
        String[] clmns = {"i_id", "i_iname", "i_description", "i_quantity", "i_amount", "i_date"};
        Config conf = new Config();
        conf.viewRecord(cqry, hrds, clmns);
    }

    public void updateItemPawn() {
        Config conf = new Config();
        System.out.print("Enter ItemPawn ID to update: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        while (conf.getSingleValue("SELECT COUNT(*) FROM itempawn WHERE i_id = ?", id) == 0) {
            System.out.println("Selected ID does not exist. Please try again.");
            id = sc.nextInt();
            sc.nextLine(); // Consume newline
        }



        Double iquantity = getValidDoubleInput("Enter New Quantity: ");
        Double iamount = getValidDoubleInput("Enter New Amount: ");

        LocalDate currdate = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yy/MM/dd");
        String date = currdate.format(format);

        String qry = "UPDATE itempawn SET i_quantity = ?, i_amount = ?, i_date = ? WHERE i_id = ?";
        conf.updateRecord(qry,  iquantity, iamount, date, id);
    }

    public void deleteItemPawn() {
        Config conf = new Config();
        System.out.print("Enter ItemPawn ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine(); // Consume newline

        while (conf.getSingleValue("SELECT COUNT(*) FROM itempawn WHERE i_id = ?", id) == 0) {
            System.out.println("Selected ID does not exist. Please try again.");
            id = sc.nextInt();
            sc.nextLine(); // Consume newline
        }

        String sqlDelete = "DELETE FROM itempawn WHERE i_id = ?";
        conf.deleteRecord(sqlDelete, id);
        System.out.println("ItemPawn with ID " + id + " has been deleted.");
    }

    // Utility method to ensure valid double input for quantity and amount
    private Double getValidDoubleInput(String prompt) {
        Double value = null;
        while (value == null) {
            System.out.print(prompt);
            if (sc.hasNextDouble()) {
                value = sc.nextDouble();
            } else {
                System.out.println("Invalid input. Please enter a numeric value.");
                sc.next();  // Clear the invalid input
            }
        }
        sc.nextLine(); // Consume newline after nextDouble()
        return value;
    }
}
