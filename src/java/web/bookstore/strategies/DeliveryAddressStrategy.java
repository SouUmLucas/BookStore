package web.bookstore.strategies;

import java.util.ArrayList;
import web.bookstore.domain.Client;
import web.bookstore.domain.DeliveryAddress;
import web.bookstore.domain.DomainEntity;

public class DeliveryAddressStrategy implements IStrategy {

    @Override
    public String process(DomainEntity entity) {
        Client client = (Client) entity;
        StringBuilder b = new StringBuilder();
        ArrayList<DeliveryAddress> deliveryAddresses;
        deliveryAddresses = (ArrayList<DeliveryAddress>) client.getDeliveryAddresses();
        
        for(DeliveryAddress d : deliveryAddresses) {
            if (d.getCity().equals("")) {
                b.append("Cidade deve ser informada\n");
            }
            if (d.getNeighborhood().equals("")) {
                b.append("Bairro deve ser informado\n");
            }
            if (d.getNumber().equals("")) {
                b.append("Numbero deve ser informado\n");
            }
            if (d.getResidenceType().equals("")) {
                b.append("Tipo de residencia deve ser informado\n");
            }
            if (d.getState().equals("")) {
                b.append("Estado deve ser informado\n");
            }
            if (d.getStreetName().equals("")) {
                b.append("Rua deve ser informado\n");
            }
            if (d.getStreetType().equals("")) {
                b.append("Logradouro deve ser informado\n");
            }
            if (d.getZipCode().equals("")) {
                b.append("CEP deve ser informado\n");
            }
        }
        
        return b.toString();
    }
}
