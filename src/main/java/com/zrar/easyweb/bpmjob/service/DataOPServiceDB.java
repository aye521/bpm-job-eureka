package com.zrar.easyweb.bpmjob.service;

import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Service
public class DataOPServiceDB {
    @Resource
    IBusinessDataSyncDao dataSyncDao;
    @Resource
    WorkFlowStartService workFlowStartService;
    @Resource
    BpmRuntimeFeignService feignService;
    @Resource
    WorkFlowCompleteService workFlowCompleteService;
    private Logger logger = LoggerFactory.getLogger(DataOPServiceDB.class);

    public void testFeign() throws Exception {
        final String businesKey = feignService.getBusinesKey("593009");
        logger.info("get business key : {}", businesKey);
        workFlowCompleteService.completeTask("admin");
    }

    public void updateStatus(String id, String status) {
        dataSyncDao.updateTaskStatus(id, status);
    }

    public void startWF() {
//        workFlowStartService.startWorkFlow("", "");
    }

}
