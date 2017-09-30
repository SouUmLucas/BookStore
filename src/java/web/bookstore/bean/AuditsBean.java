package web.bookstore.bean;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import web.bookstore.domain.Audit;

@ManagedBean(name = "MBAudits")
@SessionScoped
public class AuditsBean extends AbstractBean{
    
    private Audit audit;
    private ArrayList<Audit> audits;

    public Audit getAudit() {
        return audit;
    }

    public void setAudit(Audit audit) {
        this.audit = audit;
    }

    public ArrayList<Audit> getAudits() {
        return audits;
    }

    public void setAudits(ArrayList<Audit> audits) {
        this.audits = audits;
    }
            
    @PostConstruct
    public void init() {
        audit = new Audit();
        audits = new ArrayList<>();
    }

    @Override
    public void actionCrud(String action) {
        result = process(action, audit);
        audits = (ArrayList<Audit>) (List<?>) result.getEntities();
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
