package com.zrar.easyweb.bpmjob.dao;

import java.util.List;
import java.util.Map;

public interface IBusinessDataSyncDao {

    /**
     * 获取需要启动流程的数据列表
     * @return 数据列表
     */
    List<Map<String, String>> getScannableData();

    /**
     * 根据表名获取业务主键
     * @param tableName 表名
     * @return 业务主键
     */
    List<String> getItemIDs(String tableName);

    /**
     * 获取详细业务数据
     * @param businessKey 业务主键
     * @return 业务信息列表
     */
    List<Map<String, String>> getDetail(String businessKey);

    /**
     *  更新流程runId
     * @param businessKey 业务主键
     */
    void updateRunId(String tableName, String businessKey, String runId);

    /**
     * 根据流程实例id查询businessKey
     * @param instId 实例id
     * @return businessKey
     */
    String getBizKeyByInstId(String instId);

    /**
     * 根据获取补正数据总数
     * @param businesskey 业务主键
     * @return 补正数据总数
     */
    Integer getCorrectionCount(String businesskey);

    /**
     * 根据businessKey更新任务状态
     * @param businessKey 业务主键
     * @param status 任务状态
     * @return 更新结果
     */
    void updateTaskStatus(String businessKey, String status);
}
