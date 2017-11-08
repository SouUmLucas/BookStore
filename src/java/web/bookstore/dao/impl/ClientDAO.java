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
import web.bookstore.core.Facade;
import web.bookstore.core.IFacade;
import web.bookstore.dao.factory.DAOFactory;
import web.bookstore.domain.Client;
import web.bookstore.domain.ClientCreditCard;
import web.bookstore.domain.DeliveryAddress;
import web.bookstore.domain.DomainEntity;

public class ClientDAO extends AbstractDAO {

    public ClientDAO() {
        super("Client");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        Client client = (Client) entity;
        String sql = "INSERT INTO clients(gender, name, birthdate, cpf, phone, email, password, home_address) " +
                     "VALUES(?,?,?,?,?,?,?,?)";
        
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getGender());
            ps.setString(2, client.getName());
            ps.setDate(3, new java.sql.Date(client.getBirthdate().getTime()));
            ps.setString(4, client.getCpf());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getPassword());
            ps.setString(8, client.getHomeAddress());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    client.setId(generatedKey.getInt(1));
                }
            }
            saveRelations(client);
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return client;     
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        Client client = (Client) entity;
        
        String sql = "UPDATE clients " +
                     "SET gender = ?, name = ?, birthdate = ?, cpf = ?, phone = ?, email = ?, password = ?, home_address = ? , active = ?" +
                     "WHERE id = ?";
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, client.getGender());
            ps.setString(2, client.getName());
            ps.setDate(3, new java.sql.Date(client.getBirthdate().getTime()));
            ps.setString(4, client.getCpf());
            ps.setString(5, client.getPhone());
            ps.setString(6, client.getEmail());
            ps.setString(7, client.getPassword());
            ps.setString(8, client.getHomeAddress());
            ps.setBoolean(9, client.isActive());
            ps.setInt(10, client.getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
            }
            
            updateRelations(client);
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return client;
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        String sql = "SELECT * FROM clients WHERE id = " + entity.getId();
        Client client = new Client();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
                client.setId(result.getInt("id"));
                client.setBirthdate(result.getDate("birthdate"));
                client.setCpf(result.getString("cpf"));
                client.setEmail(result.getString("email"));
                client.setGender(result.getString("gender"));
                client.setHomeAddress(result.getString("home_address"));
                client.setName(result.getString("name"));
                client.setPassword(result.getString("password"));
                client.setPhone(result.getString("phone"));
                client.setActive(result.getBoolean("active"));
                
                selectRelations(client);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return client;
    }

    @Override
    public boolean delete(DomainEntity entity) {
        Client client = (Client) entity;
        String sql = "DELETE FROM clients WHERE id = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, client.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        String sql = "SELECT * FROM clients";
        List<DomainEntity> clients = new ArrayList<>();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                Client client = new Client();
                client.setId(result.getInt("id"));
                client.setBirthdate(result.getDate("birthdate"));
                client.setCpf(result.getString("cpf"));
                client.setEmail(result.getString("email"));
                client.setGender(result.getString("gender"));
                client.setHomeAddress(result.getString("home_address"));
                client.setName(result.getString("name"));
                client.setPassword(result.getString("password"));
                client.setPhone(result.getString("phone"));
                client.setActive(result.getBoolean("active"));
                selectRelations(client);
                
                clients.add(client);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(ClientDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return clients;
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean saveRelations(DomainEntity entity) {
        IFacade facade = new Facade();
        Client client = (Client) entity;
        for(ClientCreditCard c : client.getClientCreditCards()) {
            c.setClient(client);
            facade.save(c);
        }
        
        for(DeliveryAddress d : client.getDeliveryAddresses()) {
            d.setClient(client);
            facade.save(d);
        }
        return true;
    }

    private void updateRelations(DomainEntity entity) {
        IFacade facade = new Facade();
        Client client = (Client) entity;
        for(ClientCreditCard c : client.getClientCreditCards()) {
            c.setClient(client);
            
            if(c.getId() != 0) 
                facade.update(c);
            else
                facade.save(c);
        }
        
        for(DeliveryAddress d : client.getDeliveryAddresses()) {
            d.setClient(client);
            
            if (d.getId() != 0)
                facade.update(d);
            else
                facade.save(d);
        }
    }

    private void selectRelations(DomainEntity entity) {
        Client client = (Client) entity;
        
        DeliveryAddressDAO deliveryAddressDAO = new DeliveryAddressDAO();
        ArrayList<DeliveryAddress> deliveryAddresses;
        deliveryAddresses = (ArrayList<DeliveryAddress>) (List<?>) deliveryAddressDAO.list(client);
        client.setDeliveryAddresses(deliveryAddresses);
        
        ClientCreditCardDAO clientCreditDAO = new ClientCreditCardDAO();
        ArrayList<ClientCreditCard> clientCreditCards;
        clientCreditCards = (ArrayList<ClientCreditCard>) (List<?>) clientCreditDAO.list(client);
        client.setClientCreditCards(clientCreditCards);
    }
    
}
