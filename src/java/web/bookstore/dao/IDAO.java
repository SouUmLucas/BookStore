package web.bookstore.dao;

import java.util.List;
import web.bookstore.domain.DomainEntity;

public interface IDAO {
    public DomainEntity save(DomainEntity entity);
    public DomainEntity update(DomainEntity entity);
    public DomainEntity select(DomainEntity entity);
    public boolean delete(DomainEntity entity);
    public List<DomainEntity> list(DomainEntity entity);
    public List<DomainEntity> search(DomainEntity entity);
}
