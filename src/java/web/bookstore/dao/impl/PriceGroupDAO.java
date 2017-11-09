package web.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import web.bookstore.dao.factory.DAOFactory;
import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.PriceGroup;

public class PriceGroupDAO extends AbstractDAO {

    @Override
    public DomainEntity save(DomainEntity entity) {
        PriceGroup priceGroup = (PriceGroup) entity;
        String sql = "INSERT INTO pricegroup(name) VALUES (?)";
        
       try {
           Connection conn = DAOFactory.connect();
           PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
           ps.setString(1, priceGroup.getName());
           
           if(ps.executeUpdate() > 0) {
               ResultSet generatedKey = ps.getGeneratedKeys();
               if(generatedKey.next()) {
                   priceGroup.setId(generatedKey.getInt(1));
               }
           }
       } catch(SQLException e) {
           e.printStackTrace();
       } 
        
       return priceGroup;
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    //@Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
