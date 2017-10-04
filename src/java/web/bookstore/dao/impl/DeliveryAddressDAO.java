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
import web.bookstore.domain.DeliveryAddress;
import web.bookstore.domain.DomainEntity;

public class DeliveryAddressDAO extends AbstractDAO {
    
    private Client client;

    public void setClient(Client client) {
        this.client = client;
    }

    public DeliveryAddressDAO() {
        super("DeliveryAddress");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        DeliveryAddress deliveryAddress = (DeliveryAddress) entity;
        String sql = "INSERT INTO deliveryaddresses(fk_client, residence_type, street_type, street_name, number, neighborhood, zipcode, city, state, country) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?)";
        
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, deliveryAddress.getClient().getId());
            ps.setString(2, deliveryAddress.getResidenceType());
            ps.setString(3, deliveryAddress.getStreetType());
            ps.setString(4, deliveryAddress.getStreetName());
            ps.setString(5, deliveryAddress.getNumber());
            ps.setString(6, deliveryAddress.getNeighborhood());
            ps.setString(7, deliveryAddress.getZipCode());
            ps.setString(8, deliveryAddress.getCity());
            ps.setString(9, deliveryAddress.getState());
            ps.setString(10, deliveryAddress.getCountry());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    deliveryAddress.setId(generatedKey.getInt(1));
                }
            }
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deliveryAddress;  
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        DeliveryAddress deliveryAddress = (DeliveryAddress) entity;
        
        String sql = "UPDATE public.deliveryaddresses " +
                     "SET fk_client= ?, residence_type= ?, street_type= ?, street_name= ?, " +
                     "number= ?, neighborhood= ?, zipcode= ?, city= ?, state= ?, country= ? " +
                     "WHERE id = ?";
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, deliveryAddress.getClient().getId());
            ps.setString(2, deliveryAddress.getResidenceType());
            ps.setString(3, deliveryAddress.getStreetType());
            ps.setString(4, deliveryAddress.getStreetName());
            ps.setString(5, deliveryAddress.getNumber());
            ps.setString(6, deliveryAddress.getNeighborhood());
            ps.setString(7, deliveryAddress.getZipCode());
            ps.setString(8, deliveryAddress.getCity());
            ps.setString(9, deliveryAddress.getState());
            ps.setString(10, deliveryAddress.getCountry());
            ps.setInt(11, deliveryAddress.getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
            }
            
            ps.close();
        } catch(SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deliveryAddress;
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        String sql = "SELECT * FROM deliveryaddresses WHERE id = " + entity.getId();
        DeliveryAddress deliveryAddress = new DeliveryAddress();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
                deliveryAddress.setId(result.getInt("id"));
                deliveryAddress.setCity(result.getString("city"));
                deliveryAddress.setClient(client);
                deliveryAddress.setCountry(result.getString("country"));
                deliveryAddress.setNeighborhood(result.getString("neighborhood"));
                deliveryAddress.setNumber(result.getString("number"));
                deliveryAddress.setResidenceType(result.getString("residence_type"));
                deliveryAddress.setState(result.getString("state"));
                deliveryAddress.setStreetName(result.getString("street_name"));
                deliveryAddress.setStreetType(result.getString("street_type"));
                deliveryAddress.setZipCode(result.getString("zipcode"));
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return client;
    }

    @Override
    public boolean delete(DomainEntity entity) {
        DeliveryAddress deliveryAddress = (DeliveryAddress) entity;
        String sql = "DELETE FROM deliveryaddresses WHERE id = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, deliveryAddress.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        String sql = "SELECT * FROM deliveryaddresses WHERE id = " + entity.getId();
        List<DomainEntity> deliveryAddresses = new ArrayList<>();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                DeliveryAddress deliveryAddress = new DeliveryAddress();
                deliveryAddress.setId(result.getInt("id"));
                deliveryAddress.setCity(result.getString("city"));
                deliveryAddress.setClient(client);
                deliveryAddress.setCountry(result.getString("country"));
                deliveryAddress.setNeighborhood(result.getString("neighborhood"));
                deliveryAddress.setNumber(result.getString("number"));
                deliveryAddress.setResidenceType(result.getString("residence_type"));
                deliveryAddress.setState(result.getString("state"));
                deliveryAddress.setStreetName(result.getString("street_name"));
                deliveryAddress.setStreetType(result.getString("street_type"));
                deliveryAddress.setZipCode(result.getString("zipcode"));
                deliveryAddresses.add(deliveryAddress);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(DeliveryAddressDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return deliveryAddresses;
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
