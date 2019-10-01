package com.zrar.easyweb.bpmjob.feign;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.FeignConfig;
import com.zrar.easyweb.bpmjob.feign.impl.BpmRuntimeFeignServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 流程运行Restful接口访问的代理类
 * @company 安人股份
 */
@FeignClient(name="bpm-runtime-eureka",fallback= BpmRuntimeFeignServiceImpl.class, configuration= FeignConfig.class)
public interface BpmRuntimeFeignService {
	
	@RequestMapping(value = "/runtime/instance/v1/start", method = RequestMethod.POST)
	ObjectNode start(@RequestBody ObjectNode startFlowParamObject) throws Exception;

    @RequestMapping(value = "/runtime/task/v1/getTodoList", method = RequestMethod.POST)
    ObjectNode getTodoList(@RequestBody ObjectNode queryObj) throws Exception;

    @RequestMapping(value = "/runtime/task/v1/complete", method = RequestMethod.POST)
    ObjectNode completeTask(@RequestBody ObjectNode taskObj) throws Exception;
	
	@RequestMapping(value = "/runtime/dataSync/v1/bizkey", method = RequestMethod.GET)
    String getBusinesKey(@RequestParam(value = "instId") String instId) throws Exception;

	@RequestMapping(value = "/runtime/instance/v1/getDataByDefId", method = RequestMethod.GET)
    List<ObjectNode> getDataByDefId(@RequestParam(value = "defId", required = true) String defId) throws Exception;

	@RequestMapping(value = "/runtime/instance/v1/getDataByInst", method = RequestMethod.GET)
    List<ObjectNode> getDataByInst(@RequestParam(value = "instId", required = true) String instId) throws Exception;

    @RequestMapping(value = "/runtime/instance/v1/isSynchronize", method = RequestMethod.GET)
    Boolean isSynchronize(@RequestParam(value = "instId", required = true) String instId, @RequestParam(value = "nodeIds", required = true) String nodeIds,
                          @RequestParam(value = "status", required = true) String status, @RequestParam(value = "lastStatus", required = true) String lastStatus,
                          @RequestParam(value = "lastNodeIds", required = true) String lastNodeIds) throws Exception;

}