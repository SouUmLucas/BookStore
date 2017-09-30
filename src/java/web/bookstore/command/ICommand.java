package web.bookstore.command;

import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;

public interface ICommand {
    
    public Result execute(DomainEntity entity);
    
}
