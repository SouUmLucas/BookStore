package web.bookstore.domain;

import java.util.ArrayList;
import java.util.List;

public class Result {
    
    private String message;
    private List<DomainEntity> entities;
    private DomainEntity entity;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DomainEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<DomainEntity> entities) {
        this.entities = entities;
    }

    public DomainEntity getEntity() {
        return entity;
    }

    public void setEntity(DomainEntity entity) {
        this.entity = entity;
    }
}
