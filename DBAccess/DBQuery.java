package DBAccess;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * The DBQuery class is used to query the data base.
 * */
public class DBQuery {

    private static Statement statement; // This is my statement reference
    // creating a statement object
    public static void setStatement(Connection conn) throws SQLException {

        statement = conn.createStatement();

    }
    public static Statement getStatement(){
        return statement;
    }
}
