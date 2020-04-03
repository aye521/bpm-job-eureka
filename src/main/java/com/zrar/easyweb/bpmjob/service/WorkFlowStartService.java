package com.zrar.easyweb.bpmjob.service;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.JsonUtil;
import com.zrar.easyweb.bpmjob.dao.IBusinessDataSyncDao;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import org.apache.commons.collections.map.CaseInsensitiveMap;
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
    public void startWorkFlow(String tableName, String actDefId, String syscode , String data){
        final List<String> itemIDs = dataSyncDao.getItemIDs(tableName);
        logger.info("需要启动流程-{}的业务记录总数 : {}", actDefId, itemIDs.size());
        itemIDs.forEach( businessKey -> {
            ObjectNode startFlowParam = JsonUtil.getMapper().createObjectNode();
            startFlowParam.put("flowKey", actDefId);
            startFlowParam.put("account", flowAccount);
            startFlowParam.put("sysCode", syscode);
            //在线表单（非外部表导入）,启动不需要businesskey，必须传入空的数据结构，否则流程启动后表单展示不出来
            startFlowParam.put("data", data);
//            startFlowParam.put("businessKey", businessKey);
            //url表单frame，需要增加配置来确定表单类型，目前的代码只适用于内置表单
            startFlowParam.put("formType", "inner");
            // 构建vars流程变量,必须现在流程定义中先定义好变量
//            final List<Map<String, String>> detail = dataSyncDao.getDetail(businessKey);
//            if (detail.size() > 0) {
//                final CaseInsensitiveMap mapdata = new CaseInsensitiveMap(detail.get(0));
//                ObjectNode varsObject = JsonUtil.getMapper().createObjectNode();
//                //法人
//                varsObject.put("SF_LegalName", (String) mapdata.get("CORPNAME"));
//                //公司名称
//                varsObject.put("SF_CorpName", (String) mapdata.get("COMPANY"));
//                //首次提交时间
//                varsObject.put("SF_RED_CREATE_TIME", (String) mapdata.get("FIRSTTIME"));
//                //有效期限
//                varsObject.put("SF_PromiseDate", (String) mapdata.get("HANDLELIMIT"));
//                //最后提交时间
//                varsObject.put("SF_e_updated_date", (String) mapdata.get("MODIFYTIME"));
//                //退回次数
//                varsObject.put("SF_StdExamine", String.valueOf(mapdata.get("SUBMITNUM")));
//                //是否退回
//                varsObject.put("SF_return_status", (String) mapdata.get("CORRECTION"));
//                startFlowParam.set("vars", varsObject);
//            }
            try {
                final ObjectNode startFlowResult = bpmRuntimeFeignService.start(startFlowParam);
                String instanceId = startFlowResult.get("instId").asText();
                logger.info("流程启动成功，结果信息 ：{}",startFlowResult);
                try {
                    dataSyncDao.updateRunId("item_user_apply", businessKey, instanceId);
                } catch (Exception e) {
                    logger.error("更新业务表流程实例id失败：{} , caused by : {}", e.getMessage(), e.getCause());
                }
            } catch (Exception e) {
                logger.error("流程启动异常：{}", startFlowParam);
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
