import com.mysql.cj.jdbc.Driver;

import java.sql.*;
import java.util.Scanner;

public class Main {
    private final static String url = "jdbc:mysql://localhost:3306/hotel_db";
    private final static String username = "root";
    private final static String password = "Azad@8088";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        try {
            Connection con = DriverManager.getConnection(url, username, password);
            while (true) {
                System.out.println();
                System.out.println("Hotel Management System");
                Scanner sc = new Scanner(System.in);
                System.out.println("1. Reserve a room");
                System.out.println("2 view Reservation");
                System.out.println("3 Get Room Number");
                System.out.println("4. Update Reservations");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.println("chose an option :");
                int choice = sc.nextInt();
                switch (choice) {
                    case 1:
                        reserveRoom(con, sc);
                        break;
                    case 2:
                        viewReservation(con);
                        break;
                    case 3:
                        getRoomNumber(con, sc);
                        break;
                    case 4:
                        updateReservation(con, sc);
                        break;
                    case 5:
                        deleteReservation(con, sc);
                        break;
                    case 0:
                       try {
                           exit();
                           System.out.println("Thank you visiting our Hotel ");
                           System.exit(0);
                       }
                       catch (Exception e){
                           System.out.println(e.getMessage());
                       }
                        sc.close();
                        return;
                    default:
                        System.out.println("Invalid Choice. Try again.");


                }

            }

        } catch (SQLException s) {
            System.out.println(s.getMessage());
        }
    }

    private static void reserveRoom(Connection con, Scanner sc) {
        try {
            sc.nextLine();
            System.out.println("enter the guest name:");
            String guestName = sc.nextLine();

            System.out.println("enter the room number:");
            int roomNumber = sc.nextInt();
            sc.nextLine();
            System.out.println("enter the contact number:");
            String contactDetails = sc.nextLine();
            String sql = "insert into reservations (guest_name,room_number,contact_number)values(?,?,?)";


            try (PreparedStatement state = con.prepareStatement(sql)) {
                state.setString(1, guestName);
                state.setInt(2, roomNumber);
                state.setString(3, contactDetails);
                int row_affected = state.executeUpdate();
                if (row_affected > 0) {
                    System.out.println("The Reservation is completed Successfullly");
                } else {
                    System.out.println("The Reservation is failed");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void viewReservation(Connection con) {
        try {
            String query = "SELECT * FROM reservations";
            PreparedStatement state = con.prepareStatement(query);
            ResultSet res = state.executeQuery();


            System.out.println("+---------------+--------------+-------------+-----------------+---------------------+");
            System.out.println("| ReservationID | Guest Name   | Room Number | Contact Number  | Reservation Date    |");
            System.out.println("+---------------+--------------+-------------+-----------------+---------------------+");


            while (res.next()) {
                int reservationId = res.getInt("reservation_id");
                String guestName = res.getString("guest_name");
                int roomNumber = res.getInt("room_number");
                String contactNumber = res.getString("contact_number");
                Timestamp date = res.getTimestamp("reservation_date");


                System.out.printf("| %-13d | %-12s | %-11d | %-15s | %-19s |\n",
                        reservationId, guestName, roomNumber, contactNumber, date);
            }

            System.out.println("+---------------+--------------+-------------+-----------------+---------------------+");

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void getRoomNumber(Connection con, Scanner sc) {
        try {
            System.out.println("Enter the resrvation id to be search for room number:");
            int reserveId = sc.nextInt();
            String query = "select room_number from reservations where reservation_id = ?";

            PreparedStatement state = con.prepareStatement(query);
            state.setInt(1, reserveId);
            ResultSet res = state.executeQuery();
            while (res.next()) {
                int roomNumber = res.getInt("room_number");
                System.out.println("The Room Number For This reservation_id  " + reserveId + " is:" + roomNumber);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void updateReservation(Connection con, Scanner sc) {
        try {
sc.nextLine();
            System.out.println("enter the column name(guest_name,room_number,contact_number");

            String column = sc.nextLine().trim();
            if(!isValidchech(column)){
                System.out.println("invalid column name.");
                return;
            }
            String query = "update reservations set 4"+ column+ " = ? where reservation_id = ?";
            System.out.println("enter the reservation id should be updated:");
            int resereID = sc.nextInt();
            sc.nextLine();

            System.out.println("enter the "+column+ " updated value ");
            String update = sc.nextLine();

            PreparedStatement state = con.prepareStatement(query);
            state.setString(1, update);

            state.setInt(2,resereID);

            state.executeUpdate();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void deleteReservation(Connection con, Scanner sc) {
        try {
            System.out.println("enter the reservation id should be deleted:");
            int reserveId = sc.nextInt();

            String query = "delete from reservations where reservation_id = ?";
            PreparedStatement state = con.prepareStatement(query);
            state.setInt(1, reserveId);

            int row_affected = state.executeUpdate();
            if(row_affected>0){
                System.out.println("delete operation  is successfully ");
            }
            else{
                System.out.println("delete operation failed");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
static  boolean isValidchech(String column){
        return column.equals("guest_name")||column.equals("room_number")||column.equals("contact_number");
}
public  static  void exit() throws  InterruptedException {
    System.out.print("Existing System");
    int i=5;
    while(i!=0){
        System.out.print(".");
        Thread.sleep(450);
        i--;
    }
    System.out.println();
}





}
