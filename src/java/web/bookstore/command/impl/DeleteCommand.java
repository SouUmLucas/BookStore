package web.bookstore.command.impl;

import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;

public class DeleteCommand extends AbstractCommand {

    @Override
    public Result execute(DomainEntity entity) {
        return facade.delete(entity);
    }
    
}
