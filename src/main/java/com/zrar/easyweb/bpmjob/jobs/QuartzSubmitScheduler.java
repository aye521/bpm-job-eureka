package com.zrar.easyweb.bpmjob.jobs;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

@Configuration
public class QuartzSubmitScheduler {
    @Value("${configData.job.cron}")
    private String cron;
    @Value("${configData.job.interval}")
    private int interval;

    @Bean(name = "jobWorkflowStart")
    public JobDetailFactoryBean jobWorkflowStart() {
        return QuartzConfig.createJobDetail(WorkflowStartJob.class, "Start Workflow Job");
    }
    @Bean(name = "workflowStartTrigger")
    public SimpleTriggerFactoryBean triggerWorkflowStart(@Qualifier("jobWorkflowStart") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, interval * 60 * 1000, "Start Workflow Trigger");
    }
    @Bean(name = "jobWorkflowComplete")
    public JobDetailFactoryBean jobWorkflowComplete() {
        return QuartzConfig.createJobDetail(WorkflowCompleteJob.class, "Complete Workflow Job");
    }
    @Bean(name = "workflowCompleteTrigger")
    public CronTriggerFactoryBean triggerWorkflowComplete(@Qualifier("jobWorkflowComplete") JobDetail jobDetail) {
        return QuartzConfig.createCronTrigger(jobDetail, cron, "Complete Workflow Trigger");
    }
}
