package web.bookstore.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import web.bookstore.domain.Client;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.Result;

@ManagedBean(name = "MBClients")
@SessionScoped
public class ClientsBean extends AbstractBean {
    
    private Result result;
    private Client client;
    private ArrayList<CreditCard> creditCards;
    
    private ArrayList<Client> clients;
    private String view;
    
    @PostConstruct
    public void init() {
        client = new Client();
        domainEntity = new Client();
        //creditCards = (ArrayList<CreditCard>) (List<?>) services.get("CreditCard").getEntity();
    }
    @Override
    public void actionCrud(String action) {
        client.setId(domainEntity.getId());
        result = process(action, client);
        if(action.equals("list") || action.equals("search")) {
            clients = (ArrayList<Client>) (List<?>) result.getEntities();
        } else {
            client = (Client) result.getEntity();
        }
        
        view = this.setView(result.getMessage(), action);
    }

    @Override
    public String setView(String message, String action) {
        if(message == null) {
            message = "Completed";
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, message, message);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("message", facesMessage);
            return "sucess";
        } else {
            FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_ERROR, message, message);
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage("message", facesMessage);
        }
        return null;
    }

    @Override
    public void instance(ActionEvent event) {
        client = new Client();
        domainEntity = new Client();
    }
    
    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }

    public void setCreditCards(ArrayList<CreditCard> creditCards) {
        this.creditCards = creditCards;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients = clients;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
