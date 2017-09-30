package web.bookstore.job;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import web.bookstore.job.impl.ActivationUpdater;

public class BookActivationJobConfig {

    public static void config() {
        try {
            JobDetail job = JobBuilder.newJob(ActivationUpdater.class).build();
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("ActivationUpdater").withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *")).build();
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            scheduler.scheduleJob(job, trigger);
        } catch (SchedulerException ex) {
            Logger.getLogger(BookActivationJobConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
