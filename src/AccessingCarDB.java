import java.sql.*;
import java.util.Date;
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
            //passwords - emil 3201516950e - daniel Abcd12345
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
                    "('" + newType + "','" + newBrand + "','" + newModel + "','" + newLicense +
                    "','" + newRegDate + "','" + newOdometer + "')");

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

    public static void printCarList(ResultSet rs){
        try {
            System.out.printf("|%-10s", "Type");
            System.out.printf("|%-20s", "Brand");
            System.out.printf("|%-20s", "Model");
            System.out.printf("|%-20s", "License Plate");
            System.out.printf("|%-20s", "Registration Date");
            System.out.printf("|%-20s|\n", "Kilometers Driven");

            while(rs.next()){
                System.out.printf("|%-10s", rs.getString("carType"));
                System.out.printf("|%-20s", rs.getString("carBrand"));
                System.out.printf("|%-20s", rs.getString("carModel"));
                System.out.printf("|%-20s", rs.getString("licenseplate"));
                System.out.printf("|%-20s", rs.getString("registrationDate"));
                System.out.printf("|%-20s|\n", rs.getString("odometer"));
            }
        } catch (SQLException sqlException){
            System.out.println("SQLException\n" + sqlException.getMessage());
        }
    }

    public static void updateCar(){
        System.out.println("Which car would you like to change?\nHere's a list of the cars currently in the system:");

        try{
            con = null;
            Statement s = null;
            //Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root", "3201516950e");
            //passwords: Emil "3201516950e" - Daniel "Abcd12345" - Mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT carID, carType, carBrand, carModel, licenseplate " +
                                            "FROM cars ORDER BY carID");

            if(rs != null){
                System.out.printf("|%-3s", "ID");
                System.out.printf("|%-10s", "Type");
                System.out.printf("|%-20s", "Brand");
                System.out.printf("|%-20s", "Model");
                System.out.printf("|%-20s|\n", "License Plate");

                while(rs.next()){
                    System.out.printf("|%-3s", rs.getString("carID"));
                    System.out.printf("|%-10s", rs.getString("carType"));
                    System.out.printf("|%-20s", rs.getString("carBrand"));
                    System.out.printf("|%-20s", rs.getString("carModel"));
                    System.out.printf("|%-20s|\n", rs.getString("licenseplate"));
                }
            }

            System.out.println("Please input the ID of the car you'd like to edit (Type 0 to return)");
            int chosenCarID = Menu.getInt();

            if(chosenCarID != 0) {
                rs = s.executeQuery("SELECT carType, carBrand, carModel, licenseplate, registrationDate, odometer FROM cars WHERE carID = " + chosenCarID);

                System.out.println("This is the current information of the selected car:");
                printCarList(rs);
                System.out.println("What would you like to update");
                System.out.printf("|%-10s", "1. Type");
                System.out.printf("|%-20s", "2. Brand");
                System.out.printf("|%-20s", "3. Model");
                System.out.printf("|%-20s", "4. License Plate");
                System.out.printf("|%-20s", "5. Registration Date");
                System.out.printf("|%-20s|\n", "6. Kilometers Driven");
                System.out.println("Please input the number of the column you'd like to change:");
                Scanner console = new Scanner(System.in);

                switch (Menu.getInt()) {
                    case 1:
                        System.out.println("What should the type be?");
                        s.executeUpdate("UPDATE cars SET carType = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    case 2:
                        System.out.println("What should the brand be?");
                        s.executeUpdate("UPDATE cars SET carBrand = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    case 3:
                        System.out.println("What should the model be?");
                        s.executeUpdate("UPDATE cars SET carModel = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    case 4:
                        System.out.println("What should the license plate be?");
                        s.executeUpdate("UPDATE cars SET licenseplate = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    case 5:
                        System.out.println("What should the registration date be? (Input must be YYYY-MM-DD)");
                        s.executeUpdate("UPDATE cars SET registrationDate = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    case 6:
                        System.out.println("What should the odometer be set to?");
                        s.executeUpdate("UPDATE cars SET odometer = '" + console.nextLine() + "' WHERE carID = " + chosenCarID);
                        break;
                    default:
                        System.out.println("Input wasn't part of possible selection. No information has been changed");
                        break;
                }
                System.out.println("The car's information has been changed to: ");

                rs = s.executeQuery("SELECT carType, carBrand, carModel, licenseplate, registrationDate, odometer FROM cars WHERE carID = " + chosenCarID);
                printCarList(rs);
            }

            //Bugged, hvis man siger efter første run ændrer den ikke continueSelection til 0 når man når til det 2. gang
            /*System.out.println("Would you like to change anything else? \n1. yes \n0. No");
            int continueSelection = Menu.getInt();
            while(continueSelection != 0){
                if(continueSelection == 1){
                    AccessingDB.updateCar();
                } else{
                    System.out.println("Please type either \"1\" for yes or \"0\" for no");
                    continueSelection = Menu.getInt();
                }
            }*/

        } catch (SQLException sqlexception){
            System.out.println("SQLException\n" + sqlexception.getMessage());
        } /*catch (ClassNotFoundException classNotFoundException){
            System.out.println("ClassNotFoundException\n" + classNotFoundException.getMessage());
        }*/
    }

    public static void deleteCar(){
        System.out.println("Which car would you like to remove from the system?\nHere's a list of current cars in the system");
        try{
            con = null;
            Statement s = null;
            //Class.forName(JDBC_DRIVER);
            con = DriverManager.getConnection(DATABASE_URL, "root", "3201516950e");
            //passwords. emil "3201516950e" - daniel "Abcd12345" - mikkel ""
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT carID, carType, carBrand, carModel, licenseplate, registrationDate, odometer FROM cars ORDER BY carID");

            if(rs != null){
                System.out.printf("|%-3s", "ID");
                System.out.printf("|%-10s", "Type");
                System.out.printf("|%-20s", "Brand");
                System.out.printf("|%-20s", "Model");
                System.out.printf("|%-20s|\n", "License Plate");
                while(rs.next()){
                    System.out.printf("|%-3s", rs.getString("carID"));
                    System.out.printf("|%-10s", rs.getString("carType"));
                    System.out.printf("|%-20s", rs.getString("carBrand"));
                    System.out.printf("|%-20s", rs.getString("carModel"));
                    System.out.printf("|%-20s|\n", rs.getString("licenseplate"));
                }
            }

            System.out.println("Please input the ID of the car you wish to delete: (type 0 to return) ");
            int chosenCarID = Menu.getInt();
            if(chosenCarID != 0) {
                rs = s.executeQuery("SELECT carType, carBrand, carModel, licenseplate, registrationDate, odometer FROM cars WHERE carID = " +
                        chosenCarID);

                System.out.println("This is the car selected for deletion");
                printCarList(rs);

                System.out.println("\nAre you sure you wish to delete this car? This action can't be undone\n1. Yes\n0. No");
                int deleteSelection = Menu.getInt();
                while (deleteSelection != 0) {
                    if (deleteSelection == 1) {
                        s.executeUpdate("DELETE FROM cars WHERE carID = '" + chosenCarID + "'");
                        deleteSelection = 0;
                    } else {
                        System.out.println("Please input either 1 or 0");
                        deleteSelection = Menu.getInt();
                    }
                }
            }

        } catch (SQLException sqlException){
            System.out.println("SQLException\n" + sqlException.getMessage());
        }/* catch (ClassNotFoundException classNotFoundException){
            System.out.println("ClassNotFoundException\n" + classNotFoundException.getMessage());
        }*/
    }

}