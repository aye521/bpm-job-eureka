package com.zrar.easyweb.bpmjob.jobs;

import com.zrar.easyweb.bpmjob.base.StringUtil;
import com.zrar.easyweb.bpmjob.service.WorkFlowStartService;
import org.apache.commons.collections.map.CaseInsensitiveMap;
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
//            logger.info("start workflow : {}", map);
            final CaseInsensitiveMap insensitiveMap = new CaseInsensitiveMap(map);
            final String tname = (String) insensitiveMap.get("tname");
            final String data = (String) insensitiveMap.get("data");
            final String flowKey = (String) insensitiveMap.get("flowkey");
            if (StringUtil.isNotEmpty(tname) && StringUtil.isNotEmpty(flowKey)
                && StringUtil.isNotEmpty(data)) {
                workFlowStartService.startWorkFlow(tname, flowKey, data);
            } else {
                logger.error("请检查流程启动配置：初始化数据, 流程key, 表名，三项均不能为空");
            }
        });

    }
}
