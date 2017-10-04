package web.bookstore.domain;

import java.util.Date;
import java.util.List;

public class Client extends DomainEntity {
    private String gender;
    private String name;
    private Date birthdate;
    private String cpf;
    private String phone;
    private String email;
    private String homeAddress;
    private String password;
    private List<DeliveryAddress> deliveryAddresses;
    private List<ClientCreditCard> clientCreditCards;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public List<DeliveryAddress> getDeliveryAddresses() {
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public List<ClientCreditCard> getClientCreditCards() {
        return clientCreditCards;
    }

    public void setClientCreditCards(List<ClientCreditCard> clientCreditCards) {
        this.clientCreditCards = clientCreditCards;
    }
}
