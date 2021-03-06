package DBAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The DBPreparedStatement class is used to create prepared statements.
 * */
public class DBPreparedStatement {
    public static PreparedStatement statement;

    public static void setPreparedStatement(Connection conn, String sqlStatement) throws SQLException {
        statement = conn.prepareStatement(sqlStatement);
    }
    public static PreparedStatement getPreparedStatement(){
        return statement;
    }
}
