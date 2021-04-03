package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



public class DBConnection {


    private static final String protocal = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wqudb.ucertify.com:3306/";
    private static final String dbName = "WJ06Wtq";

    private static final String jdbcURL = protocal + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U06Wtq";
    private static Connection conn = null;

    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, Password.Password.getPassword());

            System.out.println("Connection successful");
        } catch (SQLException e){
           // System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            //System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return conn;
    }
    public static Connection getConnection(){
        return conn;
    }
    public static void closeConnection(){
        try{
            conn.close();
        }
        catch (Exception e){
            //do nothing
        }
    }
}
