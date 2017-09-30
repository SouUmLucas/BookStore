package web.bookstore.job.impl;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import web.bookstore.dao.impl.BookDAO;

public class ActivationUpdater implements Job {

    @Override
    public void execute(JobExecutionContext jec) throws JobExecutionException {
        BookDAO bookDAO = new BookDAO();
        bookDAO.updateActive();
    }
    
}
