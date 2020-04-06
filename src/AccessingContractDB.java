import java.sql.*;
import java.util.Scanner;

public class AccessingContractDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/kailuadatabase?serverTimezone=UTC";
    static Connection con;
    static String password;

    public static void setPassword(String pw){
        password = pw;
    }

    public static void listContracts() {
        try{
            con = null;
            Statement s = null;
            con = DriverManager.getConnection(DATABASE_URL, "root", password);
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT lastname, licenseplate, fromDate, toDate " +
                    "FROM rentalcontracts JOIN cars USING (carID) JOIN renters USING (renterID) ORDER BY toDate");

            if(rs != null){
                printContractList(rs);
            }

            s.close();
            con.close();
        } catch (SQLException sqlException){
            System.out.println("SQLException\n" + sqlException.getMessage());
        }
    }

    public static void createContract() {
        try {
            con = null;
            Statement s = null;
            con = DriverManager.getConnection(DATABASE_URL, "root",password);
            s = con.createStatement();

            System.out.println("Does the customer already exist in the system?\n1. Yes\n0. No");
            int existSelection = Menu.getInt();
            int chosenRenterID;
            int chosenCarID;
            Scanner console = new Scanner(System.in);

            if (existSelection == 1) {
                ResultSet rs = s.executeQuery("SELECT renterID, lastname, firstname, mobile FROM renters ORDER BY renterID");

                if (rs != null) {
                    AccessingCustomerDB.printCustomerListShort(rs);
                }

                System.out.println("Enter the ID of the renter you'd like to attach to the contract");
                chosenRenterID = Menu.getInt();
                rs = s.executeQuery("SELECT lastname, firstname, mobile, phone, street, zip, email," +
                        " licensenumber, driverSinceDate FROM renters WHERE renterID = " + chosenRenterID);
                System.out.println("This is the current information of the selected renter:");
                AccessingCustomerDB.printCustomerList(rs);
            } else {
                AccessingCustomerDB.createNewCustomer();
                chosenRenterID = AccessingCustomerDB.getLastID();
            }

            System.out.println("Select which car");
            AccessingCarDB.listCars();
            chosenCarID = Menu.getInt();

            System.out.println("From which date does the contract start? (Type YYYY-MM-DD)");
            String fromDate = console.nextLine();

            System.out.println("When does the contract end? (Type YYYY-MM-DD)");
            String toDate = console.nextLine();

            System.out.println("What is the maximum kilometers they can drive?");
            int maxKM = Menu.getInt();

            s.executeUpdate("INSERT INTO rentalcontracts (renterID, fromDate, toDate, maxKM, carID)" +
                    "VALUES ('" + chosenRenterID + "','" + fromDate + "','" + toDate + "','" + maxKM +
            "','" + chosenCarID + "')");

            s.close();
            con.close();
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }

    }

    public static void updateContract() {
        System.out.println("Which contract would you like to change?\nHere's a list of the contracts currently in the system:");

        try{
            con = null;
            Statement s = null;
            con = DriverManager.getConnection(DATABASE_URL, "root", password);
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT contractID, renterId, fromDate, toDate, maxKM, carID FROM rentalcontracts");

            if(rs != null){
                listContracts();
                System.out.println("Which contract would you like to change? (Type 0 to return)");
                int chosenContractID = Menu.getInt();

                if(chosenContractID !=0){
                    rs = s.executeQuery("SELECT renterID, fromDate, toDate, maxKM, carID FROM rentalcontracts WHERE contractID = " + chosenContractID);

                    System.out.println("This is the selected contract");

                }
            }

        } catch (SQLException sqlException){
            System.out.println("SQLException\n" + sqlException.getMessage());
        }
    }

    public static void deleteContract() {
        System.out.println("Which contract would you like to delete?\nHere's a list of current contracts in the system:");
        try {
            con = null;
            Statement s = null;
            con = DriverManager.getConnection(DATABASE_URL, "root", password);
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT contractID, lastname, licenseplate, fromDate, toDate " +
                    "FROM rentalcontracts JOIN cars USING (carID) JOIN renters USING (renterID) ORDER BY contractID");

            if(rs != null){
                printContractListID(rs);
            }
            System.out.println("Please input the ID of the contract you'd like to delete:");
            int chosenContractID = Menu.getInt();

            rs = s.executeQuery("SELECT contractID, lastname, licenseplate, fromDate, toDate " +
                    "FROM rentalcontracts JOIN cars USING (carID) JOIN renters USING (renterID) WHERE contractID = " + chosenContractID);
            System.out.println("This is the current information of the selected contract:");
            printContractListID(rs);
            System.out.println("Are you sure you want to delete this contract?\n1. Yes\n2. No");
            if (Menu.getInt() == 1) {
                s.executeUpdate("DELETE FROM rentalcontracts WHERE contractID = "+chosenContractID);
                System.out.println("Contract has been deleted from the system.\n");
            } else {
                System.out.println("No data will be deleted.\n");
            }
            s.close();
            con.close();
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void printContractList(ResultSet rs) {
        try {
            System.out.printf("|%-20s", "Last name");
            System.out.printf("|%-14s", "License plate");
            System.out.printf("|%-12s", "From");
            System.out.printf("|%-12s|\n", "To");
            while (rs.next()) {
                System.out.printf("|%-20s", rs.getString("lastname"));
                System.out.printf("|%-14s", rs.getString("licenseplate"));
                System.out.printf("|%-12s", rs.getString("fromDate"));
                System.out.printf("|%-12s|\n", rs.getString("toDate"));
            }
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void printContractListID(ResultSet rs) {
        try {
            System.out.printf("|%-3s", "ID");
            System.out.printf("|%-20s", "Last name");
            System.out.printf("|%-14s", "License plate");
            System.out.printf("|%-12s", "From");
            System.out.printf("|%-12s|\n", "To");
            while (rs.next()) {
                System.out.printf("|%-3s", rs.getString("contractID"));
                System.out.printf("|%-20s", rs.getString("lastname"));
                System.out.printf("|%-14s", rs.getString("licenseplate"));
                System.out.printf("|%-12s", rs.getString("fromDate"));
                System.out.printf("|%-12s|\n", rs.getString("toDate"));
            }
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }
    }
}
