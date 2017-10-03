package web.bookstore.domain;

public class CreditCard extends DomainEntity {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
