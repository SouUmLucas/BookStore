package web.bookstore.dao.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author lucas
 */
public class DAOFactory {
    private static final String USER = "lucas";
    private static final String PASSWORD = "admin";
    private static final String URL = "jdbc:postgresql://localhost:5432/bookstore";
    
    public static Connection connect() throws SQLException {
        DriverManager.registerDriver(new org.postgresql.Driver());
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        return connection;
    }
}
