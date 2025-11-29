package com.singer.application.controller.sv;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sv.SV03ListRequest;
import com.singer.application.dto.sv.SV04Response;
import com.singer.application.service.sv.SV02Service;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/sv03")

@RestController
@Slf4j
public class SV02Controller extends BaseController {


    @Autowired
    private SV02Service sv02Service;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
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