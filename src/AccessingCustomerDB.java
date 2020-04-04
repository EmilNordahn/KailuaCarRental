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
            con = DriverManager.getConnection(DATABASE_URL, "root","Abcd12345");
            //passwords. emil "3201516950e" - daniel "Abcd12345" - mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT lastname, firstname, mobile, phone, street, zip, email," +
                    " licensenumber, driverSinceDate FROM renters");

            if(rs!=null) {
                System.out.printf("|%-20s", "Last name");
                System.out.printf("|%-20s", "First name");
                System.out.printf("|%-10s", "Mobile");
                System.out.printf("|%-10s", "Phone");
                System.out.printf("|%-20s", "Street");
                System.out.printf("|%-10s", "Zip");
                System.out.printf("|%-20s", "Email");
                System.out.printf("|%-15s", "License number");
                System.out.printf("|%-13s|\n", "Driver since");
                while(rs.next()) {
                    System.out.printf("|%-20s", rs.getString("lastname"));
                    System.out.printf("|%-20s", rs.getString("firstname"));
                    System.out.printf("|%-10s", rs.getString("mobile"));
                    System.out.printf("|%-10s", rs.getString("phone"));
                    System.out.printf("|%-20s", rs.getString("street"));
                    System.out.printf("|%-10s", rs.getString("zip"));
                    System.out.printf("|%-20s", rs.getString("email"));
                    System.out.printf("|%-15s", rs.getString("licensenumber"));
                    System.out.printf("|%-13s|\n", rs.getString("driverSinceDate"));
                }
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
            con = DriverManager.getConnection(DATABASE_URL, "root","Abcd12345");
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
            ResultSet rs = s.executeQuery("SELECT zip FROM zips WHERE zip = "+zip);
            if (!rs.next()) {
                System.out.println("What city is this in?");
                String city = console.nextLine();
                System.out.println("What country is this in?");
                String country = console.nextLine();

                s.executeUpdate("INSERT INTO zips (zip, city, country) VALUES" +
                                "('"+ zip +"','"+ city + "','"+ country + "')");
            }

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
}
