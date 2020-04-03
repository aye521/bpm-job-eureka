package com.zrar.easyweb.bpmjob;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class QuartzTest {
    private final static Logger logger = LoggerFactory.getLogger("QuartzTest");
    private  static int sleepTime = 10;
    private static AtomicInteger fired = new AtomicInteger();
    static Scheduler scheduler;
    static DateTimeFormatter pattern = DateTimeFormatter.ofPattern("mm:ss.SSS");
    public static void main(String[] args) throws SchedulerException, InterruptedException {
        //1. build job detial
        final JobKey jobKey = JobKey.jobKey("demo-job");
        final JobDetail jobDetail = JobBuilder.newJob(JobDemo.class).withIdentity(jobKey).build();
        //2. build job trigger
        Calendar calendar = Calendar.getInstance();
        final int seconds = calendar.get(Calendar.SECOND);
        calendar.set(Calendar.SECOND, seconds + 2);
        calendar.set(Calendar.MILLISECOND, 0);
        final Calendar endate = (Calendar) calendar.clone();
        endate.set(Calendar.SECOND,endate.get(Calendar.SECOND) + 28);
        //开始时间提早一分钟，以便在应用启动时尽可能早地触发一次job的执行
//        calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 1);
        int count = 6, interval = 2;
        sleepTime = 3;
        final TriggerKey triggerKey = TriggerKey.triggerKey("demo-trigger");
        final SimpleTrigger simpleTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey)
                .endAt(endate.getTime())
                .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(count,interval)
                .withMisfireHandlingInstructionNowWithExistingCount()).startAt(calendar.getTime()).build();
        //3. scheduler
        final StdSchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Properties properties = new Properties();
        properties.setProperty("org.quartz.scheduler.instanceName", "Scheduler");
        properties.setProperty("org.quartz.scheduler.instanceId", "Inst");
        properties.setProperty("org.quartz.jobStore.class", "org.quartz.simpl.RAMJobStore");
        properties.setProperty("org.quartz.threadPool.threadCount", "3");
        properties.setProperty("org.quartz.jobStore.misfireThreshold", "1000");
        schedulerFactory.initialize(properties);

        scheduler = schedulerFactory.getScheduler();
        scheduler.scheduleJob(jobDetail, simpleTrigger);
        scheduler.getListenerManager().addJobListener(new DemoJobListener());
        scheduler.getListenerManager().addTriggerListener(new DemoTriggerListener());
        scheduler.start();

//        TimeUnit.SECONDS.sleep(4);
        logger.info("count: {}, interval: {}", count, interval);
        System.out.print(toTime(new Date()) + " main : scheduled : ");
        for (int i = 0; i < count; i++) {
            Calendar next = (Calendar) calendar.clone();
            next.add(Calendar.SECOND, i * interval);
            System.out.print((i + 1) + ". " + toTime(next.getTime()) + " > ");
        }
        System.out.println(" end.");

    }


    static class DemoJobListener implements JobListener {

        final Logger logger = LoggerFactory.getLogger(DemoJobListener.class);
        /**
         * <p>
         * Get the name of the <code>JobListener</code>.
         * </p>
         */
        @Override
        public String getName() {
            return "Demo job listener";
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
         * is about to be executed (an associated <code>{@link Trigger}</code>
         * has occurred).
         * </p>
         *
         * <p>
         * This method will not be invoked if the execution of the Job was vetoed
         * by a <code>{@link TriggerListener}</code>.
         * </p>
         *
         * @param context
         * @see #jobExecutionVetoed(JobExecutionContext)
         */
        @Override
        public void jobToBeExecuted(JobExecutionContext context) {
//                logger.info("job to be executed {}", context.getJobDetail().getKey());
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link JobDetail}</code>
         * was about to be executed (an associated <code>{@link Trigger}</code>
         * has occurred), but a <code>{@link TriggerListener}</code> vetoed it's
         * execution.
         * </p>
         *
         * @param context
         * @see #jobToBeExecuted(JobExecutionContext)
         */
        @Override
        public void jobExecutionVetoed(JobExecutionContext context) {
            logger.info("job vetoed {} at ", toTime(context.getScheduledFireTime()));
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> after a <code>{@link JobDetail}</code>
         * has been executed, and be for the associated <code>Trigger</code>'s
         * <code>triggered(xx)</code> method has been called.
         * </p>
         *
         * @param context
         * @param jobException
         */
        @Override
        public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
//            logger.info("job has been executed {}", context.getJobDetail().getKey());
        }
    }
    static class DemoTriggerListener implements TriggerListener {
        final private Logger logger = LoggerFactory.getLogger(DemoTriggerListener.class);

        /**
         * <p>
         * Get the name of the <code>TriggerListener</code>.
         * </p>
         */
        @Override
        public String getName() {
            return "demo trigger listener";
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
         * has fired, and it's associated <code>{@link JobDetail}</code>
         * is about to be executed.
         * </p>
         *
         * <p>
         * It is called before the <code>vetoJobExecution(..)</code> method of this
         * interface.
         * </p>
         *
         * @param trigger The <code>Trigger</code> that has fired.
         * @param context The <code>JobExecutionContext</code> that will be passed to
         */
        @Override
        public void triggerFired(Trigger trigger, JobExecutionContext context) {
//            logger.info("trigger fired previous: {}, next: {}",toTime(trigger.getPreviousFireTime()), toTime(trigger.getNextFireTime()));
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
         * has fired, and it's associated <code>{@link JobDetail}</code>
         * is about to be executed.  If the implementation vetos the execution (via
         * returning <code>true</code>), the job's execute method will not be called.
         * </p>
         *
         * <p>
         * It is called after the <code>triggerFired(..)</code> method of this
         * interface.
         * </p>
         *
         * @param trigger The <code>Trigger</code> that has fired.
         * @param context The <code>JobExecutionContext</code> that will be passed to
         */
        @Override
        public boolean vetoJobExecution(Trigger trigger, JobExecutionContext context) {
//            logger.info("trigger vetoJobExecution: {}, previous: {}, next: {}",toTime(context.getScheduledFireTime()), toTime(trigger.getPreviousFireTime()), toTime(trigger.getNextFireTime()));
            return false;
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
         * has misfired.
         * </p>
         *
         * <p>
         * Consideration should be given to how much time is spent in this method,
         * as it will affect all triggers that are misfiring.  If you have lots
         * of triggers misfiring at once, it could be an issue it this method
         * does a lot.
         * </p>
         *
         * @param trigger The <code>Trigger</code> that has misfired.
         */
        @Override
        public void triggerMisfired(Trigger trigger) {
            logger.info("trigger misfired previous: {}, missed: {}",toTime(trigger.getPreviousFireTime()), toTime(trigger.getNextFireTime()));
        }

        /**
         * <p>
         * Called by the <code>{@link Scheduler}</code> when a <code>{@link Trigger}</code>
         * has fired, it's associated <code>{@link JobDetail}</code>
         * has been executed, and it's <code>triggered(xx)</code> method has been
         * called.
         * </p>
         *
         * @param trigger                The <code>Trigger</code> that was fired.
         * @param context                The <code>JobExecutionContext</code> that was passed to the
         *                               <code>Job</code>'s<code>execute(xx)</code> method.
         * @param triggerInstructionCode the result of the call on the <code>Trigger</code>'s<code>triggered(xx)</code>
         */
        @Override
        public void triggerComplete(Trigger trigger, JobExecutionContext context, Trigger.CompletedExecutionInstruction triggerInstructionCode) {
//            logger.info("trigger complete {}", trigger.getKey());
        }
    }

    @DisallowConcurrentExecution
    public static class JobDemo implements Job {
        int sleep = sleepTime;
        //        public JobDemo(){}
        Logger logger = LoggerFactory.getLogger(JobDemo.class);
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            final Trigger trigger = context.getTrigger();

            logger.info("------------------- JobDemo is running about {} secs ----------------- ", sleep);
            logger.info("end time : {}, final fire time : {}", toTime(trigger.getEndTime()), toTime(trigger.getFinalFireTime()));
            logger.info("{}. scheduled: {}, fire: {}, next: {}, previous: {}", fired.addAndGet(1), toTime(context.getScheduledFireTime()),
                    toTime(context.getFireTime()), toTime(context.getNextFireTime()), toTime(context.getPreviousFireTime()));
            try {
                TimeUnit.SECONDS.sleep(sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (Objects.isNull(context.getNextFireTime())){
                logger.info("all jobs are done!");
            } else {
                logger.info("job is done!");
            }
        }
    }

    static String toTime(Date date) {
        return date == null ? null : date.toInstant().atZone(ZoneId.systemDefault()).toLocalTime().format(pattern);
    }
}
