package com.test.controller;

import com.test.util.WxconfigUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
public class WxController {


    @RequestMapping(value = "/getWxInfo1")
    @ResponseBody
   public ModelAndView getWxInfo1(HttpServletRequest request, HttpServletResponse response){
        System.out.println("微信注入信息！");
        String url1= request.getParameter("url1");
        ModelAndView ma=new ModelAndView("success");

        url1="http://www.maomi.xn--fiqs8s/getDetails/1524409397585";

      if (!" ".equals(url1) && url1!=null){
         Map map=WxconfigUtil.getWxInfo(url1);
          System.out.println(map);
          ma.addObject("res", map);

      }
       else {
          System.out.println("url为空！");
          ma.addObject("error","url空");
      }
      return ma;
    }



    @RequestMapping(value = "/getWxInfo")
    @ResponseBody
    public Map<String,String> getWxInfo(HttpServletRequest request, HttpServletResponse response){
        System.out.println("微信注入信息！");
        String url1= request.getParameter("url1");


        url1="http://www.maomi.xn--fiqs8s/getDetails/1524409397585";

        if (!" ".equals(url1) && url1!=null){
            Map map=WxconfigUtil.getWxInfo(url1);
            System.out.println(map);
            return map;

        }
        else {
            System.out.println("url为空！");

        }
        return null;
    }

}
