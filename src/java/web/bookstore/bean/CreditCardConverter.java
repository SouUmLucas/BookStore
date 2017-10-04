package web.bookstore.bean;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
 
import web.bookstore.bean.services.impl.CreditCardService;
import web.bookstore.domain.CreditCard;
 
@FacesConverter("creditCardConverter")
public class CreditCardConverter implements Converter {
 
    @Override
    public Object getAsObject(FacesContext fc, UIComponent uic, String value) {
        if(value != null && value.trim().length() > 0) {
            try {
                CreditCardService service = (CreditCardService) fc.getExternalContext().getApplicationMap().get("creditCardService");
                CreditCard cc = service.getCreditCards().get(Integer.parseInt(value) - 1);
                return cc;
            } catch(NumberFormatException e) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Conversion Error", "Not a valid theme."));
            }
        }
        else {
            return null;
        }
    }
 
    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object object) {
        if(object != null) {
            return String.valueOf(((CreditCard) object).getId());
        }
        else {
            return null;
        }
    }   
}         
   
