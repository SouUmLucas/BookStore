package web.bookstore.command.impl;

import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;

public class SelectCommand extends AbstractCommand {

    @Override
    public Result execute(DomainEntity entity) {
        return facade.select(entity);
    }
    
}
