import java.sql.*;
import java.util.Scanner;

public class AccessingCustomerDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/kailuadatabase";
    static Connection con;

    public static void listCustomers(){
        try {
            con = null;
            Statement s = null;
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root","3201516950e");
            //passwords. emil "3201516950e" - daniel "Abcd12345" - mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT lastname, firstname, mobile, phone, street, zip, email," +
                                          " licensenumber, driverSinceDate FROM renters ORDER BY lastname");

            if (rs != null) {
                printCustomerList(rs);
            }

            s.close();
            con.close();
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("ClassNotFoundException");
            System.out.println(classNotFoundException.getMessage());
            System.exit(1);
        }
    }

    public static void createNewCustomer(){
        try {
            con = null;
            Statement s = null;
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root","3201516950e");
            //passwords - emil 3201516950e - daniel Abcd12345
            s = con.createStatement();

            Scanner console = new Scanner(System.in);
            System.out.println("What is the last name of the customer?");
            String lastname = console.nextLine();
            System.out.println("What is "+lastname+"'s first name?");
            String firstname = console.nextLine();
            System.out.println("What is "+lastname+"'s mobile number?");
            String mobile = console.nextLine();
            System.out.println("What is "+lastname+"'s phone number?");
            String phone = console.nextLine();
            System.out.println("What is "+lastname+"'s street address?");
            String street = console.nextLine();

            System.out.println("What is "+lastname+"'s zip code?");
            String zip = console.nextLine();
            //method checks if zip exists in zips table and creates new row if not
            inputZip(zip, s);

            System.out.println("What is "+lastname+"'s email address?");
            String email = console.nextLine();
            System.out.println("What is "+lastname+"'s license number?");
            String licensenumber = console.nextLine();
            System.out.println("What date did "+lastname+"'s get their license? (format must be 'YYYY-MM-DD')");
            String driverSinceDate = console.nextLine();

            s.executeUpdate("INSERT INTO renters (lastname, firstname, mobile, phone, street, zip, email, licensenumber, driverSinceDate) VALUES" +
                    "('" + lastname + "','" + firstname + "','" + mobile + "','" + phone +
                    "','" + street + "','" + zip + "','" + email + "','" + licensenumber + "','" + driverSinceDate + "')");

            s.close();
            con.close();
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("ClassNotFoundException");
            System.out.println(classNotFoundException.getMessage());
            System.exit(1);
        }
    }

    public static void updateCustomer() {
        System.out.println("Which renter's information would you like to change?\nHere's a list of current renters in the system:");
        try {
            con = null;
            Statement s = null;
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root", "3201516950e");
            //passwords. emil "3201516950e" - daniel "Abcd12345" - mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT renterID, lastname, firstname, mobile FROM renters ORDER BY renterID");

            if (rs != null) {
                System.out.printf("|%-3s", "ID");
                System.out.printf("|%-20s", "Last name");
                System.out.printf("|%-20s", "First name");
                System.out.printf("|%-10s|\n", "Mobile");
                while(rs.next()) {
                    System.out.printf("|%-3s", rs.getString("renterID"));
                    System.out.printf("|%-20s", rs.getString("lastname"));
                    System.out.printf("|%-20s", rs.getString("firstname"));
                    System.out.printf("|%-10s|\n", rs.getString("mobile"));
                }
            }
            System.out.println("Please input the ID of the renter you'd like to change information for:");
            int chosenRenterID = Menu.getInt();
            rs = s.executeQuery("SELECT lastname, firstname, mobile, phone, street, zip, email," +
                                " licensenumber, driverSinceDate FROM renters WHERE renterID = "+chosenRenterID);
            System.out.println("This is the current information of the selected renter:");
            printCustomerList(rs);
            System.out.println("What would you like to update?");
            System.out.printf("|%-20s", "1. Last name");
            System.out.printf("|%-20s", "2. First name");
            System.out.printf("|%-10s", "3. Mobile");
            System.out.printf("|%-10s", "4. Phone");
            System.out.printf("|%-20s", "5. Street");
            System.out.printf("|%-10s", "6. Zip");
            System.out.printf("|%-20s", "7. Email");
            System.out.printf("|%-18s", "8. License number");
            System.out.printf("|%-16s|\n", "9. Driver since");
            System.out.println("Please input the number of the column you'd like to change:");
            Scanner console = new Scanner(System.in);
            switch(Menu.getInt()) {
                case 1:
                    System.out.println("What should the new last name be?");
                    s.executeUpdate("UPDATE renters SET lastname = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 2:
                    System.out.println("What should the new first name be?");
                    s.executeUpdate("UPDATE renters SET firstname = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 3:
                    System.out.println("What should the new mobile number be?");
                    s.executeUpdate("UPDATE renters SET mobile = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 4:
                    System.out.println("What should the new phone number be?");
                    s.executeUpdate("UPDATE renters SET phone = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 5:
                    System.out.println("What should the new street be?");
                    s.executeUpdate("UPDATE renters SET street = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    System.out.println("Would you like to update renter's zip code as well?\n1. Yes\n2. No");
                    if (Menu.getInt() == 1) {
                        System.out.println("What should the new zip be?");
                        String zip = console.nextLine();
                        inputZip(zip, s);
                        s.executeUpdate("UPDATE renters SET zip = '"+zip+"' WHERE renterID = "+chosenRenterID);
                    }
                    break;
                case 6:
                    System.out.println("What should the new zip code be?");
                    String zip = console.nextLine();
                    inputZip(zip, s);
                    s.executeUpdate("UPDATE renters SET zip = '"+zip+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 7:
                    System.out.println("What should the new email address be?");
                    s.executeUpdate("UPDATE renters SET email = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 8:
                    System.out.println("What should the new license number be?");
                    s.executeUpdate("UPDATE renters SET licensenumber = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                case 9:
                    System.out.println("Since when has this renter been a driver? (Input must be YYYY-MM-DD)");
                    s.executeUpdate("UPDATE renters SET driverSinceDate = '"+console.nextLine()+"' WHERE renterID = "+chosenRenterID);
                    break;
                default:
                    System.out.println("Input number wasn't part of possible selection. Nothing will be updated.");
                    break;
            }
            System.out.println("Renter's information has been updated to:");
            //reset resultgrid
            rs = s.executeQuery("SELECT lastname, firstname, mobile, phone, street, zip, email," +
                                " licensenumber, driverSinceDate FROM renters WHERE renterID = "+chosenRenterID);
            printCustomerList(rs);
            System.out.println();
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        } catch (ClassNotFoundException classNotFoundException) {
            System.out.println("ClassNotFoundException");
            System.out.println(classNotFoundException.getMessage());
            System.exit(1);
        }
    }

    public static void printCustomerList(ResultSet rs) {
        try {
            System.out.printf("|%-20s", "Last name");
            System.out.printf("|%-20s", "First name");
            System.out.printf("|%-10s", "Mobile");
            System.out.printf("|%-10s", "Phone");
            System.out.printf("|%-20s", "Street");
            System.out.printf("|%-10s", "Zip");
            System.out.printf("|%-20s", "Email");
            System.out.printf("|%-18s", "License number");
            System.out.printf("|%-16s|\n", "Driver since");
            while (rs.next()) {
                System.out.printf("|%-20s", rs.getString("lastname"));
                System.out.printf("|%-20s", rs.getString("firstname"));
                System.out.printf("|%-10s", rs.getString("mobile"));
                System.out.printf("|%-10s", rs.getString("phone"));
                System.out.printf("|%-20s", rs.getString("street"));
                System.out.printf("|%-10s", rs.getString("zip"));
                System.out.printf("|%-20s", rs.getString("email"));
                System.out.printf("|%-18s", rs.getString("licensenumber"));
                System.out.printf("|%-16s|\n", rs.getString("driverSinceDate"));
            }
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }
    }

    public static void inputZip(String zip, Statement s) {
        try {
            //search whether zip exists in zips table
            ResultSet rs = s.executeQuery("SELECT zip FROM zips WHERE zip = " + zip);
            if (!rs.next()) { //if not, a new zip row will be made
                System.out.println("This zip isn't in the system. Please answer the following questions.");
                Scanner console = new Scanner(System.in);
                System.out.println("What city is this in?");
                String city = console.nextLine();
                System.out.println("What country is this in?");
                String country = console.nextLine();

                s.executeUpdate("INSERT INTO zips (zip, city, country) VALUES" +
                                "('" + zip + "','" + city + "','" + country + "')");
            } else { //if exists, no more zip input is needed
                System.out.println("This zip already exists in the system. You don't need to input more location information");
            }
        } catch (SQLException sqlException) {
            System.out.println("SQLException");
            System.out.println(sqlException.getMessage());
        }
    }
}
