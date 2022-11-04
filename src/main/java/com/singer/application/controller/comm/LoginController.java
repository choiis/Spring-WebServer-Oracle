package com.singer.application.controller.comm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final Log log = LogFactory.getLog(LoginController.class);

    @GetMapping(value = "/index")
    public ModelAndView index(HttpSession session) throws Exception {

        return new ModelAndView("index");
    }

    @GetMapping(value = "/main")
    public ModelAndView goMain() throws Exception {
        ModelAndView model = new ModelAndView("/main");
        log.info("go main page");
        return model;
    }

    @RequestMapping(value = "/sessionNotExist")
    public ModelAndView redirect_uri(HttpServletRequest request) throws Exception {
        String redirect = request.getParameter("redirect_uri");
        ModelAndView model = new ModelAndView("/index");
        log.info("redirect_uri " + redirect);
        model.addObject("redirect_uri", redirect);
        return model;
    }
}
