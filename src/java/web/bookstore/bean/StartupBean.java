package web.bookstore.bean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import web.bookstore.job.BookActivationJobConfig;

@Singleton
@Startup
public class StartupBean {

    
    @PostConstruct
    private void startup() {
        BookActivationJobConfig.config();
    }
    
    @PreDestroy
    private void shutdown() {
        // application shutdown test
        System.out.println("ENCERRANDO A DROGA TODA!!!!!!!");
    }
}
