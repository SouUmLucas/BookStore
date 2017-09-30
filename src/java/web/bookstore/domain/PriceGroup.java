package web.bookstore.domain;

public class PriceGroup extends DomainEntity {
    private double price;
    private String name;
    
    public PriceGroup(int id, String name) {
        setId(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
