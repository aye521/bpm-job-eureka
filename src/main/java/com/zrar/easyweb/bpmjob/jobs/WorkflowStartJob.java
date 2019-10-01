package com.zrar.easyweb.bpmjob.jobs;

import com.zrar.easyweb.bpmjob.service.WorkFlowStartService;
import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

@DisallowConcurrentExecution
public class WorkflowStartJob implements Job {
    private Logger logger = LoggerFactory.getLogger(WorkflowStartJob.class);
    @Resource
    WorkFlowStartService workFlowStartService;

    @Override
    public void execute(JobExecutionContext context) {
        logger.info("start job : {}", context);
        workFlowStartService.getStartMetaData().forEach( map -> {
            logger.info("start workflow : {}", map);
            workFlowStartService.startWorkFlow(map.get("TABLE_NAME"), map.get("ACT_DEF_ID"));
        });

    }
}
