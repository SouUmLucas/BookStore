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
import web.bookstore.domain.Category;
import web.bookstore.domain.DomainEntity;

public class CategoryDAO extends AbstractDAO {

    public CategoryDAO() {
        super("categories");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        Category category = (Category) entity;
        String sql = "INSERT INTO categories(fk_book, name) " +
                     "VALUES(?,?)";
        
        
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, category.getBookId());
            ps.setString(2, category.getName());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    category.setId(generatedKey.getInt(1));
                }
            }
            
            
            ps.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return category;
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
        String sql = "DELETE FROM categories WHERE fk_book = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, entity.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        String sql = "SELECT * FROM categories WHERE fk_book = " + entity.getId();
        List<DomainEntity> categories = new ArrayList<>();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                Category c = new Category();
                c.setId(result.getInt("id"));
                c.setBookId(result.getInt("fk_book"));
                c.setName(result.getString("name"));
                categories.add(c);
            }
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return categories;
    }

    //@Override
    public List<DomainEntity> search(DomainEntity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
