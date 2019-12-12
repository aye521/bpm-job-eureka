package com.zrar.easyweb.bpmjob.controller;

import com.zrar.easyweb.bpmjob.service.DataOPServiceDB;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/bpm/job/data/sync")
public class OpenControlller {
    @Resource
    DataOPServiceDB dataOPServiceDB;

    @PostMapping(value = "taskstatus", produces = MediaType.TEXT_PLAIN_VALUE)
    @ApiOperation(value="更新任务状态", httpMethod = "POST", notes = "更新任务状态")
    public String updateStatus(@ApiParam(name = "id",value = "主键") @RequestParam String id,
                               @ApiParam(name = "status", value ="任务状态") @RequestParam String status) {
        dataOPServiceDB.updateStatus(id, status);
        return "更新成功";
    }

    /**
     * 心跳测试
     * @return 字符串
     */
    @GetMapping(value = "demo", produces = MediaType.TEXT_PLAIN_VALUE)
    public String heartbeat() {
        return "I'm alive plain text!";
    }

    /**
     * 测试方法
     * @param request http请求
     * @return json
     * @throws Exception 异常信息
     */
    @GetMapping(value ="test",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String test(HttpServletRequest request) throws Exception {
        String uri = request.getRequestURI();
        dataOPServiceDB.testFeign();
        return "{\"uri\":\"" + uri +  "\"}";
    }
}
