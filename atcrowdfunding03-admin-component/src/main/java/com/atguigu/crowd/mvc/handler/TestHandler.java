package com.atguigu.crowd.mvc.handler;

import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.entity.other.ParamData;
import com.atguigu.crowd.entity.other.Student;
import com.atguigu.crowd.service.api.AdminService;
import com.atguigu.crowd.utils.CrowdUtil;
import com.atguigu.crowd.utils.ResultEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class TestHandler {
    @Autowired
    private AdminService adminService;

    private Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @RequestMapping("/test/ssm.html")
    public String testSSS(ModelMap modelMap,  HttpServletRequest request) {
        boolean b = CrowdUtil.judgeRequestType(request);
        logger.info("judgeResult:" + b);
        List<Admin> adminList = adminService.getAll();
        System.out.println(adminList);
        modelMap.addAttribute("adminList", adminList);
        System.out.println("=========================================");
        System.out.println(10 / 0);
        return "target";
    }
    @RequestMapping("/send/array/one.html")
    @ResponseBody
    public String testReceiveArrayOne(@RequestParam("array[]") List<Integer> array) {
        array.forEach(number -> System.out.println(number));
        return "success";
    }
    @RequestMapping("/send/array/two.html")
    @ResponseBody
    public String testReceiveArrayTwo(ParamData paramData) {
        List<Integer> array = paramData.getArray();
        array.forEach(number -> System.out.println(number));
        return "success";
    }
    @RequestMapping("/send/array/three.html")
    @ResponseBody
    public String testReceiveArrayThree(@RequestBody List<Integer> array) {
        Logger logger = LoggerFactory.getLogger(TestHandler.class);
        array.forEach(number -> logger.info("number" + number));
        return "success";
    }
    @RequestMapping("/send/compose/object.json")
    @ResponseBody
    public ResultEntity<Student> testReceiveComposeObject(@RequestBody Student student, HttpServletRequest request) {
        boolean b = CrowdUtil.judgeRequestType(request);
        System.out.println("============================" + b);
        return ResultEntity.successWithData(student);
    }
}
