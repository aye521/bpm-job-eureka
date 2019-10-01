package com.zrar.easyweb.bpmjob.jobs;

import com.zrar.easyweb.bpmjob.service.WorkFlowCompleteService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@DisallowConcurrentExecution
public class WorkflowCompleteJob implements Job {
    private Logger logger = LoggerFactory.getLogger(WorkflowCompleteJob.class);
    @Resource
    WorkFlowCompleteService workFlowCompleteService;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        logger.info("start job : {}", context);
        workFlowCompleteService.completeTask("admin");
    }
}
