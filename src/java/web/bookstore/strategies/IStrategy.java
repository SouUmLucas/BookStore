package web.bookstore.strategies;

import web.bookstore.domain.DomainEntity;

public interface IStrategy {
    public String process(DomainEntity entity);
}
