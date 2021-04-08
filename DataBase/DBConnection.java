package DataBase;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import Password.Password;

/**
 * This is the DBConnection class, it provides the methods to make connections to the database.
 * */
public class DBConnection {


    private static final String protocal = "jdbc";
    private static final String vendorName = ":mysql:";
    private static final String ipAddress = "//wgudb.ucertify.com:3306/";
    private static final String dbName = "WJ06Wtq";

    private static final String jdbcURL = protocal + vendorName + ipAddress + dbName;

    private static final String MYSQLJBCDriver = "com.mysql.jdbc.Driver";

    private static final String username = "U06Wtq";
    private static Connection conn = null;

    /**
     * This is the startConnection method, it can be called to start a connection to the database. It is used only once
     * in the main method of the Main class. The program keeps the connection while it is open and only closes it when the
     * program is closed.
     * */
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJBCDriver);
            conn = DriverManager.getConnection(jdbcURL, username, Password.getPassword());

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
    /**
     * This is the getConnection method, which will be used throughout the program when we need to communicate with the
     * database.
     * */
    public static Connection getConnection(){
        return conn;
    }
    /**
     * This is the closeConnection method and is used to close the connection between the database and the program.
     * It is used only once in the main method of the Main class.
     * */
    public static void closeConnection(){
        try{
            conn.close();
        }
        catch (Exception e){
            //do nothing
        }
    }
}
