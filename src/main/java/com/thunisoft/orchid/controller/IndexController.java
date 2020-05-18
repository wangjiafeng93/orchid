package com.thunisoft.orchid.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;

import com.thunisoft.artery.data.annotation.ExceptionTips;
import com.thunisoft.orchid.mapper.AjxxMapper;
import com.thunisoft.orchid.service.TyywAjxxService;

/**
 * Index Controller 首页面Controller
 *
 * @author huayu
 * @date 2020-03-30
 */
@Controller
@RequestMapping("index")
public class IndexController {

    @Autowired
    public TyywAjxxService tyywAjxxService;

    @GetMapping("/getAjxx")
    public String getAjxx(HttpServletRequest request) {
        tyywAjxxService.exportTyywAjxx();
        return "index";
    }

    @GetMapping("/getXyrxx")
    public String getXyrxx(HttpServletRequest request) {
        tyywAjxxService.exportTyywXyrxx();
        return "index";
    }
    /**
     * html入口
     * 注意：如直接访问到contextPath，默认走的SpringBoot的转发，不会走此方法了
     *
     * @return 'resources/templates/index.html'
     */
    @GetMapping()
    public String index(HttpServletRequest request) {
        request.setAttribute("rs1", rs1("张三", "男"));
        return "index";
    }

    /**
     * 数据源
     *
     * @param name 姓名
     * @param sex  性别
     * @return
     */
    @ResponseBody
    @RequestMapping("/rs1")
    public Object rs1(String name, String sex) {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("sex", sex);
        return json;
    }

    /**
     * 示例
     *
     * @return
     */
    @ExceptionTips("请求example失败")
    @ResponseBody
    @GetMapping("/example")
    public String example() {
        return "example data";
    }
}