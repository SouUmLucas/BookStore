package web.bookstore.bean;

import javax.faces.event.ActionEvent;

public interface IBean {
    public void actionCrud(String action);
    public String setView(String message, String action);
    public void instance(ActionEvent event);
}
