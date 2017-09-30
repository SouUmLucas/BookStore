package web.bookstore.domain;

public class Category extends DomainEntity {
    private String name;
    private int bookId;
    
    public Category(){}
    
    public Category(String name){
        this.name = name;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
