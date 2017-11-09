package web.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.bookstore.dao.IDAO;
import web.bookstore.dao.factory.DAOFactory;

public abstract class AbstractDAO implements IDAO {
    
    String table;
    Connection connection;
    
    protected void openConnection() {
        try {
            if(connection == null || connection.isClosed()) {
                connection = DAOFactory.connect();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AbstractDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
