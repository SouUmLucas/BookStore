package web.bookstore.strategies;

import java.util.ArrayList;
import web.bookstore.domain.Client;
import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.ClientCreditCard;

public class ClientCreditCardStrategy implements IStrategy{

    @Override
    public String process(DomainEntity entity) {
        Client client = (Client) entity;
        StringBuilder b = new StringBuilder();
        
        ArrayList<ClientCreditCard> ccc = (ArrayList<ClientCreditCard>) client.getClientCreditCards();
        
        for(ClientCreditCard c : ccc) {
            if (c.getCcv().equals("")) {
                b.append("CCV deve ser informado\n");
            }
            if(c.getExpirationDate().equals("")) {
                b.append("Data de vencimento deve ser informado\n");
            }
            if(c.getNumber().equals("")) {
                b.append("Número dever ser informado\n");
            }
            if(c.getCreditCard() == null) {
                b.append("Bandeira deve ser informada\n");
            }
        }
        return b.toString();
    }
    
}
