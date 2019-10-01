package com.zrar.easyweb.bpmjob.service;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.JsonUtil;
import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class WorkFlowCompleteService {
    private Logger logger = LoggerFactory.getLogger(WorkFlowCompleteService.class);

    @Resource
    IBusinessDataSyncDao dataSyncDao;

    @Resource
    BpmRuntimeFeignService bpmRuntimeFeignService;

    @Value("${configData.pageSize:100}")
    private String pageSize;

    /**
     *  完成特定任务
     * @param account 暂时不用，目前使用Authorization的验证账号
     */
    public void completeTask(String account){
        final ObjectNode queryFilter = JsonUtil.getMapper().createObjectNode();
        final ObjectNode page = JsonUtil.getMapper().createObjectNode();
        page.put("page", 1);
        page.put("pageSize", pageSize);
        page.put("showTotal", true);
        queryFilter.set("pageBean", page);
        try {
            final ObjectNode list = bpmRuntimeFeignService.getTodoList(queryFilter);
            logger.info("待办记录总数：{}", list.get("total").asText());
            final ArrayNode rows = list.withArray("rows");
            rows.forEach( row -> {
                final String instId = row.get("procInstId").asText();
                final String taskId = row.get("taskId").asText();
                String bizKey = "";
                try {
                    bizKey = bpmRuntimeFeignService.getBusinesKey(instId);
                } catch (Exception e) {
                    logger.error("从bpm-runtime获取businessKey失败 : {}", e.getMessage());
                }
                final Integer count = dataSyncDao.getCorrectionCount(bizKey);
                logger.info("instId : {}, taskId: {}, bizKey : {}, count of correction data: {}", instId, taskId, bizKey, count);
                if (count > 0) {
                    try {
                        final ObjectNode taskObj = JsonUtil.getMapper().createObjectNode();
                        taskObj.put("taskId", taskId);
                        taskObj.put("actionName", "agree");
                        taskObj.put("opinion", "同意");
                        final ObjectNode result = bpmRuntimeFeignService.completeTask(taskObj);
                        logger.info("任务完成结果： {}", result);
                    } catch (Exception e) {
                        logger.error("完成任务失败 ： {}", e.getMessage());
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            logger.error("获取待办列表错误： {}", queryFilter);
            e.printStackTrace();
        }

    }

}
