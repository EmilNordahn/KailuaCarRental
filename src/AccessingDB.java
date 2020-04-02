import java.sql.*;

public class AccessingDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/kailuadatabase";
    static Connection con;

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