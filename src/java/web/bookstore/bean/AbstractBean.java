package web.bookstore.bean;

import java.util.HashMap;
import java.util.Map;
import web.bookstore.command.ICommand;
import web.bookstore.command.impl.DeleteCommand;
import web.bookstore.command.impl.ListCommand;
import web.bookstore.command.impl.SaveCommand;
import web.bookstore.command.impl.SearchCommand;
import web.bookstore.command.impl.SelectCommand;
import web.bookstore.command.impl.UpdateCommand;
import web.bookstore.domain.DomainEntity;
import web.bookstore.domain.Result;

public abstract class AbstractBean implements IBean {
    protected String status = "list";
    protected Result result;
    protected String view;
    protected DomainEntity domainEntity;
    protected static Map<String, ICommand> commands;
    
    public AbstractBean() {
        commands = new HashMap<>();
        commands.put("save", new SaveCommand());
        commands.put("update", new UpdateCommand());
        commands.put("select", new SelectCommand());
        commands.put("delete", new DeleteCommand());
        commands.put("list", new ListCommand());
        commands.put("search", new SearchCommand());
    }
    
    public Result process(String action, DomainEntity entity) {
        ICommand command = commands.get(action);
        return command.execute(entity);
    }
    
    public void changeStatus(String status) {
        this.status = status;
        if(status.equals("list")) {
            actionCrud("list");
        }
    }
    
    public void changeStatus(String status, int bookId) {
        this.status = status;
        domainEntity.setId(bookId);
        actionCrud("select");
    }
    
    public boolean isList() {
        return status.equals("list");
    }
    
    public boolean isNew() {
        return status.equals("new");
    }
    
    public boolean isEdit() {
        return status.equals("edit");
    }
}
