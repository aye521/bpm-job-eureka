package com.zrar.easyweb.bpmjob.feign.impl;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.zrar.easyweb.bpmjob.base.BaseException;
import com.zrar.easyweb.bpmjob.feign.BpmRuntimeFeignService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BpmRuntimeFeignServiceImpl implements BpmRuntimeFeignService {
	private static String errorMsg = "服务器开小差了， 请稍后重试。";
	
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

}
