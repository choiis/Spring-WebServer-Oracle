package com.singer.application.controller.sr;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sr.SR02Request;
import com.singer.application.dto.sr.SR02Response;
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

import com.singer.application.service.sr.SR02Service;

@RequestMapping("/sr01")
@Controller
public class SR02Controller extends BaseController {

    private final Log log = LogFactory.getLog(SR02Controller.class);

    @Autowired
    private SR02Service sr02Service;

    @ResponseBody
    @RequestMapping(value = "/sr02", method = RequestMethod.POST)
    public ResponseEntity<SR02Response> insertSR02Vo(@RequestBody @Valid SR02Request sr02Request,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 post");

        String userid = getSessionId(request);
        SR02Response sr02Response = sr02Service.insertSR02Vo(sr02Request, userid);

        log.debug("exit sr02 post");
        return new ResponseEntity<SR02Response>(sr02Response, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "sr02/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity<SR02Response> deleteSR02Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 delete");
        sr02Service.deleteSR02Vo(seq, getSessionId(request));

        log.debug("exit sr02 delete");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "sr02/{seq}", method = RequestMethod.GET)
    public ResponseEntity<SR02Response> selectOneSR02Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 get");

        String userid = getSessionId(request);
        SR02Response response = sr02Service.selectOneSR02Vo(seq, userid);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
