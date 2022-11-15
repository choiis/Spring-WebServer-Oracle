package com.singer.application.controller.sv;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sv.SV03ListRequest;
import com.singer.application.dto.sv.SV04Response;
import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.application.service.sv.SV02Service;

import lombok.extern.slf4j.Slf4j;

@RequestMapping("/sv01")
@Controller
@Slf4j
public class SV02Controller extends BaseController {


    @Autowired
    private SV02Service sv02Service;

    @ResponseBody
    @RequestMapping(value = "/sv03", method = RequestMethod.POST)
    public ResponseEntity<SV04Response> insertSV03Vo(@RequestBody SV03ListRequest listRequest,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sv03 post");

        String userid = getSessionId(request);
        sv02Service.insertSv03(listRequest, userid);

        log.debug("exit sv03 post");
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
