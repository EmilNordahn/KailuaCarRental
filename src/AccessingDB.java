import java.sql.*;
import java.util.Scanner;

public class AccessingDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/kailuadatabase";
    static Connection con;

    public static void addCar(){
        try {
            con = null;
            Statement s = null;
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root","3201516950e");
            s = con.createStatement();

            Scanner input = new Scanner(System.in);

            System.out.println("What type is the new car?");
            String newType = input.next();

            System.out.println("What brand?");
            String newBrand = input.next();

            System.out.println("What Model?");
            String newModel = input.next();

            System.out.println("What is the license plate?");
            String newLicense = input.next();

            System.out.println("What is the registration date (yyyy-mm-dd)");
            String newRegDate = input.next();

            System.out.println("How many kilometers has the car driven?");
            String newOdometer = input.next();

            s.executeUpdate("INSERT INTO cars (carType, carBrand, carModel, licenseplate, registrationDate, odometer) VALUES" +
                    "(" + newType + "," + newBrand + "," + newModel + "," + newLicense +
                    "," + newRegDate + "," + newOdometer + ")");

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


    public static void listCars(){
        try {
            con = null;
            Statement s = null;
            Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root","Abcd12345");
            //passwords. emil "3201516950e" - daniel "Abcd12345" - mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT carType, carBrand, carModel, licenseplate," +
                    " registrationDate, odometer FROM cars");

            if(rs!=null){
                while(rs.next()){
                    System.out.println("Type: " + rs.getString("carType") + "\nBrand: " + rs.getString("carBrand") +
                            "\nModel: " + rs.getString("carModel") +
                            "\nLicense plate: " + rs.getString("licenseplate") +
                            "\nRegistration date: " + rs.getString("registrationDate") +
                            "\nKilometers driven: " + rs.getString("odometer") + "\n");
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
}