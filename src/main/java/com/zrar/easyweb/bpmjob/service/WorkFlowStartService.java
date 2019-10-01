package com.zrar.easyweb.bpmjob.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.JsonUtil;
import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class WorkFlowStartService {
    private Logger logger = LoggerFactory.getLogger(WorkFlowStartService.class);

    @Resource
    IBusinessDataSyncDao dataSyncDao;

    @Resource
    BpmRuntimeFeignService bpmRuntimeFeignService;

    @Value("${configData.account.flow}")
    private String flowAccount;

    /**
     *  启动流程
     * @param tableName 业务表名
     * @param actDefId  流程定义Id
     */
    public void startWorkFlow(String tableName, String actDefId){
        final String[] tbls = tableName.split(":");
        final List<String> itemIDs = dataSyncDao.getItemIDs(tbls[0]);
        logger.info("item id : {}", itemIDs.size());
        itemIDs.forEach( businessKey -> {
            ObjectNode startFlowParam = JsonUtil.getMapper().createObjectNode();
            startFlowParam.put("flowKey", actDefId);
            startFlowParam.put("account", flowAccount);
            startFlowParam.put("businessKey", businessKey);
//            startFlowParam.put("formType", "frame");
            // 构建vars流程变量
            final List<Map<String, String>> detail = dataSyncDao.getDetail(businessKey);
            if (detail.size() > 0) {
                final Map<String, String> data = detail.get(0);
                ObjectNode varsObject = JsonUtil.getMapper().createObjectNode();
                //法人
                varsObject.put("SF_LegalName", data.get("CORPNAME"));
                //公司名称
                varsObject.put("SF_CorpName", data.get("COMPANY"));
                //首次提交时间
                varsObject.put("SF_RED_CREATE_TIME", data.get("FIRSTTIME"));
                //有效期限
                varsObject.put("SF_PromiseDate", data.get("HANDLELIMIT"));
                //最后提交时间
                varsObject.put("SF_e_updated_date", data.get("MODIFYTIME"));
                //退回次数
                varsObject.put("SF_StdExamine", data.get("SUBMITNUM"));
                //是否退回
                varsObject.put("SF_return_status", data.get("CORRECTION"));
                startFlowParam.set("vars", varsObject);
            }
            try {
                final ObjectNode startFlowResult = bpmRuntimeFeignService.start(startFlowParam);
                String instanceId = startFlowResult.get("instId").asText();
                for (String tbl : tbls) {
                    dataSyncDao.updateRunId(tbl, businessKey, instanceId);
                }
            } catch (Exception e) {
                logger.error("流程启动失败：{}", startFlowParam);
                e.printStackTrace();
            }

        });
    }

    /**
     * 获取启动流程的元数据
     * @return 启动流程的元数据
     */
    public List<Map<String, String>> getStartMetaData() {
        return dataSyncDao.getScannableData();
    }

}
