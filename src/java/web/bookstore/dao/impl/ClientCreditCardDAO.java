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
import web.bookstore.domain.Client;
import web.bookstore.domain.ClientCreditCard;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DomainEntity;

public class ClientCreditCardDAO extends AbstractDAO {
    
    private Client client;

    public void setCliente(Client cliente) {
        this.client = cliente;
    }

    public ClientCreditCardDAO() {
        super("ClientCreditCard");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        ClientCreditCard clientCreditCard = (ClientCreditCard) entity;
        String sql = "INSERT INTO clientcreditcards(fk_client, number, expiration_date, ccv, fk_creditcard) " +
                     "VALUES(?,?,?,?,?)";
        
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientCreditCard.getClient().getId());
            ps.setString(2, clientCreditCard.getNumber());
            ps.setString(3, clientCreditCard.getExpirationDate());
            ps.setString(4, clientCreditCard.getCcv());
            ps.setInt(5, clientCreditCard.getCreditCard().getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    clientCreditCard.setId(generatedKey.getInt(1));
                }
            }
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(ClientCreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientCreditCard;  
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        ClientCreditCard clientCreditCard = (ClientCreditCard) entity;
        
        String sql = "UPDATE public.clientcreditcards " +
                     " SET fk_client= ?, number = ?, expiration_date= ?, ccv= ?, fk_creditcard= ? " +
                     " WHERE id = ?";
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, clientCreditCard.getClient().getId());
            ps.setString(2, clientCreditCard.getNumber());
            ps.setString(3, clientCreditCard.getExpirationDate());
            ps.setString(4, clientCreditCard.getCcv());
            ps.setInt(5, clientCreditCard.getCreditCard().getId());
            ps.setInt(6, clientCreditCard.getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
            }
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(ClientCreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientCreditCard;
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        String sql = "SELECT * FROM clientcreditcards WHERE id = " + entity.getId();
        ClientCreditCard clientCreditCard = new ClientCreditCard();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
                clientCreditCard.setId(result.getInt("id"));
                clientCreditCard.setClient(client);
                clientCreditCard.setCreditCard(null);
                clientCreditCard.setExpirationDate(result.getString("expiration_date"));
                clientCreditCard.setNumber(result.getString("number"));
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientCreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientCreditCard;
    }

    @Override
    public boolean delete(DomainEntity entity) {
        ClientCreditCard clientCreditCard = (ClientCreditCard) entity;
        String sql = "DELETE FROM clientcreditcards WHERE id = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, clientCreditCard.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        this.client = (Client) entity;
        String sql = "SELECT * FROM clientcreditcards WHERE fk_client = " + entity.getId();
        List<DomainEntity> clientCreditCards = new ArrayList<>();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                ClientCreditCard clientCreditCard = new ClientCreditCard();
                clientCreditCard.setId(result.getInt("id"));
                clientCreditCard.setClient(client);
                
                clientCreditCard.setCreditCard(findCreditCard(result.getInt("fk_creditcard")));
                clientCreditCard.setExpirationDate(result.getString("expiration_date"));
                clientCreditCard.setNumber(result.getString("number"));
                clientCreditCard.setCcv(result.getString("ccv"));
                clientCreditCards.add(clientCreditCard);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientCreditCardDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clientCreditCards;
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private CreditCard findCreditCard(int id) {
        CreditCardDAO dao = new CreditCardDAO();
        CreditCard cc = new CreditCard();
        cc.setId(id);
        return (CreditCard) dao.select(cc);
    }
}
