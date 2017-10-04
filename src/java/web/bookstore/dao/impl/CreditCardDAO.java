package web.bookstore.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import web.bookstore.dao.factory.DAOFactory;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DomainEntity;

public class CreditCardDAO extends AbstractDAO {

    public CreditCardDAO() {
        super("CreditCards");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        CreditCard creditCard = (CreditCard) entity;
        String sql = "INSERT INTO CreditCards(name) " +
                     "VALUES(?)";
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, creditCard.getName());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    creditCard.setId(generatedKey.getInt(1));
                }
            }
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(CreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return creditCard;
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        CreditCard creditCard = (CreditCard) entity;
        
        String sql = "UPDATE CreditCards " +
                     "SET name = ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, creditCard.getName());
            ps.setInt(2, creditCard.getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    creditCard.setId(generatedKey.getInt(1));
                }
            }

            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(CreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return creditCard;
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        String sql = "SELECT * FROM CreditCards WHERE id = " + entity.getId();
        CreditCard creditCard = new CreditCard();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
                creditCard.setId(result.getInt("id"));
                creditCard.setName(result.getString("name"));
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(CreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return creditCard;
    }

    @Override
    public boolean delete(DomainEntity entity) {
        CreditCard creditCard = (CreditCard) entity;
        String sql = "DELETE FROM CreditCards WHERE id = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, creditCard.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(CreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        String sql = "SELECT * FROM CreditCards";
        List<DomainEntity> creditCards = new ArrayList<>();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                CreditCard creditCard = new CreditCard();
                creditCard.setId(result.getInt("id"));
                creditCard.setName(result.getString("name"));
                creditCards.add(creditCard);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return creditCards;
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
