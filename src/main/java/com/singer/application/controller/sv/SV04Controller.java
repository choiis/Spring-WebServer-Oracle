package com.singer.application.controller.sv;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sv.SV04ListResponse;
import com.singer.application.dto.sv.SV04Request;
import com.singer.application.dto.sv.SV04Response;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.singer.application.service.sv.SV04Service;

@RequestMapping("/sv01")
@Controller
public class SV04Controller extends BaseController {

    private final Log log = LogFactory.getLog(SV04Controller.class);

    @Autowired
    private SV04Service sv04Service;

    @ResponseBody
    @RequestMapping(value = "/sv04/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SV04ListResponse> selectSV04Vo(@PathVariable int seq01,
        @PathVariable int parents, @PathVariable int nowPage, HttpServletRequest request)
        throws Exception {
        log.debug("enter sv04 get");

        String userid = getSessionId(request);

        SV04ListResponse listResponse = sv04Service.selectSV04Vo(seq01, parents, nowPage, userid);

        log.debug("exit sv04 get");
        return new ResponseEntity<SV04ListResponse>(listResponse, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sv04", method = RequestMethod.POST)
    public ResponseEntity<SV04Response> insertSV04Vo(@RequestBody @Valid SV04Request sv04Request,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sv04 post");

        String userid = getSessionId(request);

        SV04Response sv04Response = sv04Service.insertSV04Vo(sv04Request, userid);

        log.debug("exit sv04 post");
        return new ResponseEntity<SV04Response>(sv04Response, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/sv04/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
    public ResponseEntity<SV04Response> deleteSV04Vo(@PathVariable int seq,
        @PathVariable int seq01, @PathVariable int parents, HttpServletRequest request)
        throws Exception {
        log.debug("enter sv04 delete");

        sv04Service.deleteSV04Vo(seq, seq01, parents);

        log.debug("exit sv04 delete");
        return new ResponseEntity<SV04Response>(HttpStatus.NO_CONTENT);
    }
}
