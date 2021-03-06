package com.taotao.sso.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.sso.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 第二十五颗星星
 * @create 2018-01-23 19:31
 * @desc 用户控制层
 **/
@Controller
public class UserController {
    @Resource
    private UserService userService;
    @Value("COOKIE_TOKEN_KEY")
    private String COOKIE_TOKEN_KEY;

    @RequestMapping("/user/check/{param}/{type}")
    @ResponseBody
    public TaotaoResult checkUser(@PathVariable String param, @PathVariable int type) {
        TaotaoResult taotaoResult = userService.checkData(param, type);
        return taotaoResult;
    }

    @RequestMapping(value = "/user/register", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult checkUser(TbUser tbUser) {
        TaotaoResult taotaoResult = userService.createUser(tbUser);
        return taotaoResult;
    }

    @RequestMapping(value = "/user/login", method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult login(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        TaotaoResult result = userService.login(username, password);
        //取出token信息,存入cookie中,实现跨域
        String token = result.getData().toString();
        CookieUtils.setCookie(request, response,COOKIE_TOKEN_KEY+":",token);
        return result;
    }
}