package web.bookstore.strategies;

import web.bookstore.domain.Client;
import web.bookstore.domain.DomainEntity;

public class PasswordConfirmationStrategy implements IStrategy {

    @Override
    public String process(DomainEntity entity) {
        Client client = (Client) entity;
        if(!client.getPassword().equals(client.getPasswordConfirmation())) {
            return "Password confimation does not match";
        }
        return null;
    }
    
}
