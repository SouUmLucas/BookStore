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
import web.bookstore.domain.ClientCreditCard;
import web.bookstore.domain.CreditCard;
import web.bookstore.domain.DeliveryAddress;
import web.bookstore.domain.Result;

@ManagedBean(name = "MBClients")
@SessionScoped
public class ClientsBean extends AbstractBean {
    
    private Result result;
    private Client client;
    private ArrayList<CreditCard> creditCards;
    private ArrayList<ClientCreditCard> clientCreditCards;
    private ArrayList<DeliveryAddress> deliveryAddresses;
    
    private ArrayList<Client> clients;
    private String view;
    
    @PostConstruct
    public void init() {
        client = new Client();
        domainEntity = new Client();
        
        // relations
        clientCreditCards = new ArrayList<>();
        deliveryAddresses = new ArrayList<>();
        
        // must have at least one credit card and delivery address
        clientCreditCards.add(new ClientCreditCard());
        deliveryAddresses.add(new DeliveryAddress());
        creditCards = (ArrayList<CreditCard>) (List<?>) services.get("CreditCards").getEntity();
    }
    @Override
    public void actionCrud(String action) {
        client.setId(domainEntity.getId());
        client.setDeliveryAddresses(deliveryAddresses);
        client.setClientCreditCards(clientCreditCards);
        
        result = process(action, client);
        if(action.equals("list") || action.equals("search")) {
            clients = (ArrayList<Client>) (List<?>) result.getEntities();
        } else {
            client = (Client) result.getEntity();
            clientCreditCards = (ArrayList<ClientCreditCard>) client.getClientCreditCards();
            deliveryAddresses = (ArrayList<DeliveryAddress>) client.getDeliveryAddresses();
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
    
    public void addClientCreditCard() {
        clientCreditCards.add(new ClientCreditCard());
    }
    
    public void addDeliveryAddress() {
        deliveryAddresses.add(new DeliveryAddress());
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

    public ArrayList<ClientCreditCard> getClientCreditCards() {
        return clientCreditCards;
    }

    public void setClientCreditCards(ArrayList<ClientCreditCard> clientCreditCards) {
        this.clientCreditCards = clientCreditCards;
    }

    public ArrayList<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(ArrayList<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public String getView() {
        return view;
    }

    public void setView(String view) {
        this.view = view;
    }
}
