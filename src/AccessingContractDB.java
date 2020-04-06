import java.sql.*;

public class AccessingContractDB {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DATABASE_URL = "jdbc:mysql://localhost:3306/kailuadatabase?serverTimezone=UTC";
    static Connection con;
    static final String password = "Abcd12345";

    public static void listContracts() {
        try{
            con = null;
            Statement s = null;
            con = DriverManager.getConnection(DATABASE_URL, "root", password);
            s = con.createStatement();

            ResultSet rs = s.executeQuery("SELECT lastname, licenseplate, fromDate, toDate " +
                    "FROM rentalcontracts JOIN cars USING (carID) JOIN renters USING (renterID) ORDER BY lastname");

            if(rs != null){
                printContractList(rs);
            }

            s.close();
            con.close();
        } catch (SQLException sqlException){
            System.out.println("SQLException\n" + sqlException.getMessage());
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
}
