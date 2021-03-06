package web.bookstore.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import web.bookstore.dao.IDAO;
import web.bookstore.dao.impl.AuditDAO;
import web.bookstore.dao.impl.BookDAO;
import web.bookstore.dao.impl.ClientCreditCardDAO;
import web.bookstore.dao.impl.ClientDAO;
import web.bookstore.dao.impl.CreditCardDAO;
import web.bookstore.dao.impl.DeliveryAddressDAO;
import web.bookstore.dao.impl.OrderDAO;
import web.bookstore.domain.Audit;
import web.bookstore.domain.Book;
import web.bookstore.domain.Client;
import web.bookstore.domain.ClientCreditCard;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DeliveryAddress;
import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;
import web.bookstore.domain.Order;
import web.bookstore.strategies.ClientStrategy;
import web.bookstore.strategies.IStrategy;
import web.bookstore.strategies.PasswordConfirmationStrategy;

public class Facade implements IFacade {
    private Map<String, IDAO> daos;
    private Map<String, List<IStrategy>> bookStrategy;
    private Map<String, List<IStrategy>> clientStrategy;
    private Map<String, List<IStrategy>> clientCreditCardsStrategies;
    private Map<String, List<IStrategy>> DeliveryAddressesStrategies;
    
    private Map<String, Map<String, List<IStrategy>>> strategies;
    private Result result;
    
    public Facade() {
        daos = new HashMap<>();
        
        daos.put(Book.class.getName(), new BookDAO());
        daos.put(Audit.class.getName(), new AuditDAO());
        daos.put(Client.class.getName(), new ClientDAO());
        daos.put(CreditCard.class.getName(), new CreditCardDAO());
        daos.put(ClientCreditCard.class.getName(), new ClientCreditCardDAO());
        daos.put(DeliveryAddress.class.getName(), new DeliveryAddressDAO());
        daos.put(Order.class.getName(), new OrderDAO());
                
        // Client Strategies
        clientStrategy = new HashMap<>();
        List<IStrategy> clientSaveStrategies = new ArrayList<>();
        clientSaveStrategies.add(new PasswordConfirmationStrategy());
        clientSaveStrategies.add(new ClientStrategy());
        clientStrategy.put("save", clientSaveStrategies);
        clientStrategy.put("update", clientSaveStrategies);
        
        // Client's credit cards Strategies
        clientCreditCardsStrategies = new HashMap<>();
        
        // Delivery addresses Strategies
        DeliveryAddressesStrategies = new HashMap<>();
        
        // Books Strategies
        bookStrategy = new HashMap<>();
        
        // All entities strategies
        strategies = new HashMap<>();
        strategies.put(Client.class.getName(), clientStrategy);
    }

    @Override
    public Result save(DomainEntity entity) {
        result = new Result();
        result.setEntity(entity);
        String message = validate(entity, "save");
        
        if(message == null) {
            IDAO dao = daos.get(entity.getClass().getName());
            DomainEntity ent = dao.save(entity);
            result.setEntity(ent);
        } else
            result.setMessage(message);

        return result;
    }

    @Override
    public Result select(DomainEntity entity) {
        result = new Result();
        IDAO dao = daos.get(entity.getClass().getName());
        DomainEntity ent = dao.select(entity);
        result.setEntity(ent);
        return result;
    }

    @Override
    public Result update(DomainEntity entity) {
        result = new Result();
        result.setEntity(entity);
        String message = validate(entity, "update");
        
        if(message == null) {
            IDAO dao = daos.get(entity.getClass().getName());
            DomainEntity ent = dao.update(entity);
            result.setEntity(ent);
        } else
            result.setMessage(message);

        return result;
    }

    @Override
    public Result delete(DomainEntity entity) {
        result = new Result();
        String message = validate(entity, "delete");
        if(message == null) {
            IDAO dao = daos.get(entity.getClass().getName());
            dao.delete(entity);
            result.setEntity(new Book());
            result.setMessage("Excluído com sucesso!");
        } else {
            result.setMessage(message);
        }
        
        return result;
    }
    
    private String validate(DomainEntity entity, String action) {
        Map<String, List<IStrategy>> rules = strategies.get(entity.getClass().getName());
        StringBuilder message = new StringBuilder();
        
        if (rules != null) {
            List<IStrategy> entityStrategies = rules.get(action);
            
            if(entityStrategies != null) {
                for(IStrategy strategy : entityStrategies) {
                    String m = strategy.process(entity);
                    if(m != null) {
                        message.append(m);
                        message.append("\n");
                    }
                }
            }
        }
        
        if(message.length() > 0)
            return message.toString();
        
        return null;
    }

    @Override
    public Result list(DomainEntity entity) {
        result = new Result();
        IDAO dao = daos.get(entity.getClass().getName());
        List<DomainEntity> entities = dao.list(entity);
        result.setEntities(entities);
        return result;
    }

    @Override
    public Result search(DomainEntity entity) {
        result = new Result();
        IDAO dao = daos.get(entity.getClass().getName());
        List<DomainEntity> entities = dao.search(entity);
        result.setEntities(entities);
        return result;
    }
    
}
