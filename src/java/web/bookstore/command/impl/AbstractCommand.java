package web.bookstore.command.impl;

import web.bookstore.command.ICommand;
import web.bookstore.core.Facade;
import web.bookstore.core.IFacade;

public abstract class AbstractCommand implements ICommand {
    
    IFacade facade = new Facade();
    
}
