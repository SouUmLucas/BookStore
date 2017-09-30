package web.bookstore.dao.impl;

import java.lang.reflect.Field;
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
import web.bookstore.domain.Book;
import web.bookstore.domain.Category;
import web.bookstore.domain.DomainEntity;

public class BookDAO extends AbstractDAO {

    public BookDAO() {
        super("books");
    }

    @Override
    public DomainEntity save(DomainEntity entity) {
        Book book = (Book) entity;
        String sql = "INSERT INTO books(author, year, title, publisher, edition, isbn, pages, synopsis, dimensions, pricegroup, barcode) " +
                     "VALUES(?,?,?,?,?,?,?,?,?,?,?)";
        audit(book, "INSERT");
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getAuthor());
            ps.setString(2, book.getYear());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getEdition());
            ps.setString(6, book.getIsbn());
            ps.setString(7, book.getPages());
            ps.setString(8, book.getSynopsis());
            ps.setString(9, book.getDimensions());
            ps.setString(10, book.getPriceGroup());
            ps.setString(11, book.getBarCode());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    book.setId(generatedKey.getInt(1));
                }
            }
            CategoryDAO categoryDAO = new CategoryDAO();
            
            for(String c : book.getCategories()) {
                Category cat = new Category();
                cat.setBookId(book.getId());
                cat.setName(c);
                categoryDAO.save(cat);
            }
            ps.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return book;
    }

    @Override
    public DomainEntity update(DomainEntity entity) {
        Book book = (Book) entity;
        CategoryDAO categoryDAO = new CategoryDAO();
        categoryDAO.delete(entity);
        String sql = "UPDATE public.books " +
                     "SET author= ?, year= ?, title= ?, publisher= ?, edition= ?, isbn= ?, " + 
                     "pages= ?, synopsis= ?, dimensions= ?, pricegroup= ?, barcode= ? , isactive= ?, reason= ? "+
                     "WHERE id = ?";
        audit(book, "update");
        try (Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, book.getAuthor());
            ps.setString(2, book.getYear());
            ps.setString(3, book.getTitle());
            ps.setString(4, book.getPublisher());
            ps.setString(5, book.getEdition());
            ps.setString(6, book.getIsbn());
            ps.setString(7, book.getPages());
            ps.setString(8, book.getSynopsis());
            ps.setString(9, book.getDimensions());
            ps.setString(10, book.getPriceGroup());
            ps.setString(11, book.getBarCode());
            ps.setBoolean(12, book.isActive());
            ps.setString(13, book.getReason());
            ps.setInt(14, book.getId());

            if(ps.executeUpdate() > 0) {
                ResultSet generatedKey = ps.getGeneratedKeys();
                if(generatedKey.next()) {
                    book.setId(generatedKey.getInt(1));
                }
            }
            
            for(String c : book.getCategories()) {
                Category cat = new Category();
                cat.setBookId(book.getId());
                cat.setName(c);
                categoryDAO.save(cat);
            }
            
            ps.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
        
        return book;
    }

    @Override
    public DomainEntity select(DomainEntity entity) {
        String sql = "SELECT * FROM books WHERE id = " + entity.getId();
        Book book = new Book();
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            if(result.next()) {
                book.setId(result.getInt("id"));
                book.setAuthor(result.getString("author"));
                book.setBarCode(result.getString("barcode"));
                book.setDimensions(result.getString("dimensions"));
                book.setEdition(result.getString("edition"));
                book.setIsbn(result.getString("isbn"));
                book.setPages(result.getString("pages"));
                book.setPriceGroup(result.getString("pricegroup"));
                book.setPublisher(result.getString("publisher"));
                book.setSynopsis(result.getString("synopsis"));
                book.setTitle(result.getString("title"));
                book.setYear(result.getString("year"));
                book.setActive(result.getBoolean("isactive"));
                book.setReason(result.getString("reason"));
                
                CategoryDAO categoryDAO = new CategoryDAO();
                ArrayList<String> categories = (ArrayList<String>) (ArrayList<?>) categoryDAO.list(book);
                book.setCategories(categories);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return book;
    }

    @Override
    public boolean delete(DomainEntity entity) {
        Book book = (Book) entity;
        String sql = "DELETE FROM books WHERE id = ?";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, book.getId());
            ps.executeUpdate();
        } catch (SQLException ex) {
             Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

    @Override
    public List<DomainEntity> list(DomainEntity entity) {
        List<DomainEntity> books = new ArrayList<>();
        String sql = "SELECT * FROM books";
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                Book book = new Book();
                book.setId(result.getInt("id"));
                book.setAuthor(result.getString("author"));
                book.setBarCode(result.getString("barcode"));
                book.setDimensions(result.getString("dimensions"));
                book.setEdition(result.getString("edition"));
                book.setIsbn(result.getString("isbn"));
                book.setPages(result.getString("pages"));
                book.setPriceGroup(result.getString("pricegroup"));
                book.setPublisher(result.getString("publisher"));
                book.setSynopsis(result.getString("synopsis"));
                book.setTitle(result.getString("title"));
                book.setYear(result.getString("year"));
                book.setActive(result.getBoolean("isactive"));
                book.setReason(result.getString("reason"));
                books.add(book);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return books;
    }
    
    public void updateActive() {
        String sql = "UPDATE books SET isactive = false, reason = 'SEM ESTOQUE' WHERE isactive = true";
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void audit(Book newBook, String action) {
        Book oldBook = (Book) select(newBook);
        String sql = "INSERT INTO audits(username, action, resource, resource_id, field, old_value, new_value) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try(Connection conn = DAOFactory.connect()) {
            if(action.equals("update")) {
                Field[] fields = Book.class.getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    Object oldValue = field.get(oldBook);
                    if (oldValue == null) {
                        oldValue = "";
                    }
                    Object newValue = field.get(newBook);
                    if (newValue == null) {
                        newValue = "";
                    }
                    if(!newValue.equals(oldValue)) {
                        PreparedStatement ps = conn.prepareStatement(sql);
                        ps.setString(1, "LUCAS");
                        ps.setString(2, action);
                        ps.setString(3, "Book");
                        ps.setInt(4, newBook.getId());
                        ps.setString(5, field.getName());
                        ps.setString(6, oldValue.toString());
                        ps.setString(7, newValue.toString());
                        ps.executeUpdate();
                    }
                    field.setAccessible(false);
                }
            } else {
                Field[] fields = Book.class.getDeclaredFields();
                for(Field field : fields) {
                    field.setAccessible(true);
                    Object newValue = field.get(newBook);
                    if (newValue == null) {
                        newValue = "";
                    }
                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setString(1, "LUCAS");
                    ps.setString(2, action);
                    ps.setString(3, "Book");
                    ps.setInt(4, newBook.getId());
                    ps.setString(5, field.getName());
                    ps.setString(6, "");
                    ps.setString(7, newValue.toString());
                    ps.executeUpdate();
                    field.setAccessible(false);
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public List<DomainEntity> search(DomainEntity entity) {
        Book b =(Book) entity;
        List<DomainEntity> books = new ArrayList<>();
        String sql = "SELECT * FROM books WHERE ";
        sql += " ID != 0 ";
        
        if(!b.getYear().equals("")) {
            sql += "AND year = '" + b.getYear()+ "' ";
        }
        if(!b.getTitle().equals("")) {
            sql += "AND title LIKE '%" + b.getTitle()+ "%' ";
        }
        
        if(!b.getAuthor().equals("")) {
            sql += "AND author LIKE '%" + b.getAuthor() + "%' ";
        }
        
        if(!b.getDimensions().equals("")) {
            sql += "AND dimensions LIKE '%" + b.getDimensions()+ "%' ";
        }
        
        if(!b.getEdition().equals("")) {
            sql += "AND edition LIKE '%" + b.getEdition()+ "%' ";
        }
        
        if(!b.getIsbn().equals("")) {
            sql += "AND isbn LIKE '%" + b.getIsbn()+ "%' ";
        }
        
        if(!b.getPages().equals("")) {
            sql += "AND pages LIKE '%" + b.getPages()+ "%' ";
        }
        
        if(!b.getPriceGroup().equals("")) {
            sql += "AND pricegroup = '" + b.getPriceGroup()+ "' ";
        }
        
        if(!b.getPublisher().equals("")) {
            sql += "AND publisher LIKE '%" + b.getPublisher()+ "%' ";
        }
        
        if(!b.getReason().equals("")) {
            sql += "AND reason LIKE '%" + b.getReason()+ "%' ";
        }
        
        if(!b.getSynopsis().equals("")) {
            sql += "AND synopsis LIKE '%" + b.getSynopsis()+ "%' ";
        }
        
        try(Connection conn = DAOFactory.connect()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet result = ps.executeQuery();
            
            while(result.next()) {
                Book book = new Book();
                book.setId(result.getInt("id"));
                book.setAuthor(result.getString("author"));
                book.setBarCode(result.getString("barcode"));
                book.setDimensions(result.getString("dimensions"));
                book.setEdition(result.getString("edition"));
                book.setIsbn(result.getString("isbn"));
                book.setPages(result.getString("pages"));
                book.setPriceGroup(result.getString("pricegroup"));
                book.setPublisher(result.getString("publisher"));
                book.setSynopsis(result.getString("synopsis"));
                book.setTitle(result.getString("title"));
                book.setYear(result.getString("year"));
                book.setActive(result.getBoolean("isactive"));
                book.setReason(result.getString("reason"));
                books.add(book);
            }
            ps.close();
        } catch (SQLException ex) {
            Logger.getLogger(BookDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return books;
    }
}
