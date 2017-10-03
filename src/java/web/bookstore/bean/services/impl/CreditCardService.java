package web.bookstore.bean.services.impl;

import java.util.List;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DomainEntity;

public class CreditCardService extends AbstractService {
    @Override
    public List<DomainEntity> getEntity() {
        return facade.list(new CreditCard()).getEntities();
    }
}
