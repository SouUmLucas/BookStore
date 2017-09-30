package web.bookstore.core;

import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;

public interface IFacade {
    
    public Result save(DomainEntity entity);
    public Result select(DomainEntity entity);
    public Result update(DomainEntity entity);
    public Result delete(DomainEntity entity);
    public Result list(DomainEntity entity);
    public Result search(DomainEntity entity);

}
