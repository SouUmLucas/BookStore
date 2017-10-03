package web.bookstore.bean.services.impl;

import web.bookstore.bean.services.IService;
import web.bookstore.core.Facade;
import web.bookstore.core.IFacade;

public abstract class AbstractService implements IService {
    IFacade facade = new Facade();
}
