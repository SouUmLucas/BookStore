package web.bookstore.bean.services.impl;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DomainEntity;

@ManagedBean(name="creditCardService", eager = true)
@ApplicationScoped
public class CreditCardService extends AbstractService {
    private List<CreditCard> creditCards;
    
    @Override
    public List<DomainEntity> getEntity() {
        return facade.list(new CreditCard()).getEntities();
    }
    
    public List<CreditCard> getCreditCards() {
        this.creditCards = (ArrayList<CreditCard>) (List<?>) facade.list(new CreditCard()).getEntities();
        return creditCards;
    }
}
