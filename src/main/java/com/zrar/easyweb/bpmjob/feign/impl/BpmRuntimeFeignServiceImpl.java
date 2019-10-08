package com.zrar.easyweb.bpmjob.feign.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.BaseException;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BpmRuntimeFeignServiceImpl implements FallbackFactory<BpmRuntimeFeignService> {
	private static String errorMsg = "服务器开小差了， 请稍后重试。";
    private Logger logger = LoggerFactory.getLogger(BpmRuntimeFeignService.class);

    @Override
    public BpmRuntimeFeignService create(Throwable cause) {
        logger.error("调用 Feign 失败 ： {} ", cause.getMessage());
        return new BpmRuntimeFeignService() {
            @Override
            public ObjectNode start(ObjectNode startFlowParamObject) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public ObjectNode getTodoList(ObjectNode queryObj) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public ObjectNode completeTask(ObjectNode taskObj) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public String getBusinesKey(String instId) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public List<ObjectNode> getDataByDefId(String defId) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public List<ObjectNode> getDataByInst(String instId) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }

            @Override
            public Boolean isSynchronize(String instId, String nodeIds, String status,String lastStatus,String lastNodeIds) throws Exception {
                throw new BaseException(HttpStatus.INTERNAL_SERVER_ERROR, 500, errorMsg);
            }
        };
    }
}
