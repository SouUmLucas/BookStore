package web.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.bookstore.dao.factory.DAOFactory;
import web.bookstore.domain.Audit;
import web.bookstore.domain.DomainEntity;

/**
 *
 * @author lucas
 */
public class AuditDAO extends AbstractDAO {

    public AuditDAO() {
        super("audit");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean delete(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        List<DomainEntity> audits = new ArrayList<>();
        String sql = "SELECT * FROM audits";
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                Audit audit = new Audit();
                audit.setId(result.getInt("id"));
                audit.setUsername(result.getString("username"));
                audit.setAction(result.getString("action"));
                audit.setResource(result.getString("resource"));
                audit.setResourceId(result.getInt("resource_id"));
                audit.setField(result.getString("field"));
                audit.setOldValue(result.getString("old_value"));
                audit.setNewValue(result.getString("new_value"));
                audits.add(audit);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return audits;
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
