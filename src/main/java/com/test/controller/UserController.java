package com.test.controller;

import com.test.model.Student;
import com.test.service.impl.StudentServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RequestMapping("userController/")
@Controller
public class UserController {

    @Resource
    public StudentServiceImpl studentServiceImpl;

    @RequestMapping("loginVertifiled")
    public Student loginVertifiled(HttpServletRequest request, HttpServletResponse response){
        System.out.println("跳转至controller层！");

        request.getParameter("id");



        return null;
    }


}
