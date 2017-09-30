package web.bookstore.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import web.bookstore.domain.Book;
import web.bookstore.domain.Category;
import web.bookstore.domain.Result;

@ManagedBean(name = "MBBooks")
@SessionScoped
public class BooksBean extends AbstractBean {

    private Book book;
    private ArrayList<Book> booksList;
    private ArrayList<Category> categories;
    private Result result;
    private String view;
    
    @PostConstruct
    public void init() {
        book = new Book();
        domainEntity = new Book();
        booksList = new ArrayList<>();
        
        categories = new ArrayList<>();
        categories.add(new Category("Ação"));
        categories.add(new Category("Aventura"));
        categories.add(new Category("Suspense"));
    }
    
    public ArrayList<Category> getCategories() {
        return categories;
    }
    
    public void setCategories(ArrayList<Category> categories) {
        this.categories = categories;
    }
    
    public Book getBook() {
        return book;
    }
    
    public void setBook(Book book) {
        this.book = book;
    }
    
    public ArrayList<Book> getBooksList() {
        return booksList;
    }
    
    public void setBooksList(ArrayList<Book> booksList) {
        this.booksList = booksList;
    }

    @Override
    public void actionCrud(String action) {
        book.setId(domainEntity.getId());
        result = process(action, book);
        if(action.equals("list") || action.equals("search")) {
            booksList = (ArrayList<Book>) (List<?>) result.getEntities();
        } else {
            book = (Book) result.getEntity();
        }
        
        view = this.setView(result.getMessage(), action);
    }

    @Override
    public String setView(String message, String action) {
        if(message == null) {
            message = "Completed";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("message", facesMessage);
            return "sucess";
        } else {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("message", facesMessage);
        }
        return null;
    }

    @Override
    public void instance(ActionEvent event) {
        book = new Book();
        domainEntity = new Book();
    }
    
}
