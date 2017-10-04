package web.bookstore.strategies;

import web.bookstore.domain.Client;
import web.bookstore.domain.DomainEntity;

public class ClientStrategy implements IStrategy{

    @Override
    public String process(DomainEntity entity) {
        Client client = (Client) entity;
        
        ClientCreditCardStrategy stCreditCard = new ClientCreditCardStrategy();
        DeliveryAddressStrategy stDelivery = new DeliveryAddressStrategy();
        
        StringBuilder b = new StringBuilder();
        if (client.getBirthdate() == null) {
            b.append("Data de nascimento deve ser preenchida\n");
        }
        if (client.getCpf().equals("")) {
            b.append("CPF deve ser preenchido\n");
        }
        if (client.getEmail().equals("")) {
            b.append("Email deve ser preenchido\n");
        }
        if (client.getGender().equals("")) {
            b.append("Genero deve ser preenchido\n");
        }
        if (client.getHomeAddress().equals("")) {
            b.append("Endereço residêncial deve ser preenchido\n");
        }
        if (client.getName().equals("")) {
            b.append("Nome deve ser preenchdo\n");
        }
        if (client.getPassword().equals("")) {
            b.append("Senha deve ser informada\n");
        }
        if (client.getPhone().equals("")) {
            b.append("Telefone deve ser informado\n");
        }
        
        b.append(stCreditCard.process(entity));
        b.append(stDelivery.process(entity));
        
        String msg = b.toString();
        if (!msg.equals(""))
            return msg;
        return null;
    }
    
}
