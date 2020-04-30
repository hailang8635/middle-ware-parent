package com.drool.engine.controller;

import com.alibaba.fastjson.JSONObject;
import com.smart.demo_smart.demo.Person2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.kie.api.KieServices;
import org.kie.api.command.Command;
import org.kie.api.command.KieCommands;
import org.kie.api.runtime.ExecutionResults;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedList;
import java.util.List;

@Api("KIE规则引擎接口")
@RequestMapping("/demo/kie")
@RestController
public class KieServerController {

    private Logger log = LoggerFactory.getLogger(KieServerController.class);

    @Value("${kie-server.url:http://112.65.210.206:8103/kie-server/services/rest/server}")
    public String SERVER_URL;

    @Value("${kie-server.username:admin}")
    public String USERNAME;
    @Value("${kie-server.password:admin}")
    public String PASSWORD;

    @Value("${kie-server.kie-container-id:demo-smart}")
    public String KIE_CONTAINER_ID;

    @Value("${kie-server.session-id:session2}")
    public String SESSION_ID;

    /**
     * {"name": "张三", "age": 19, "profession":{"title":"中级"}}
     * @param personParam
     * @return
     */
    @ApiOperation("kie 示例1")
    @RequestMapping(value = "/test1", method = {RequestMethod.POST})
    public Object param (@RequestBody Person2 personParam){

        // KisService 配置信息设置
        KieServicesConfiguration kieServicesConfiguration =
                KieServicesFactory.newRestConfiguration(SERVER_URL, USERNAME, PASSWORD, 100000L);
        kieServicesConfiguration.setMarshallingFormat(MarshallingFormat.JSON);

        // 创建规则服务客户端
        KieServicesClient kieServicesClient = KieServicesFactory.newKieServicesClient(kieServicesConfiguration);
        RuleServicesClient ruleServicesClient = kieServicesClient.getServicesClient(RuleServicesClient.class);

        // 规则输入条件
//        Person2 person = new Person2();
//        person.setAge(30);
//
//        Profession2 profession =new Profession2();
//        profession.setTitle("中级");
//        person.setProfession(profession);

        // 命令定义，包含插入数据，执行规则
        KieCommands kieCommands = KieServices.Factory.get().getCommands();
        List<Command<?>> commands = new LinkedList<>();
        commands.add(kieCommands.newInsert(personParam, "person"));
        commands.add(kieCommands.newFireAllRules());
        ServiceResponse<ExecutionResults> results = ruleServicesClient.executeCommandsWithResults(KIE_CONTAINER_ID,
                kieCommands.newBatchExecution(commands, SESSION_ID));

        // 返回值读取
        Person2 person2 = (Person2) results.getResult().getValue("person");

        log.debug(JSONObject.toJSONString(person2));
        return person2;

    }

    public static void main(String[] args) {

    }
}
