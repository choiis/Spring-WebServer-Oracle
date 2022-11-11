package com.singer.application.controller.sb;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sb.SB02ListResponse;
import com.singer.application.dto.sb.SB02Request;
import com.singer.application.dto.sb.SB02Response;
import com.singer.application.service.sb.SB02Service;

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

@RequestMapping("/sb01")
@Controller
public class SB02Controller extends BaseController {

    private final Log log = LogFactory.getLog(SB02Controller.class);

    @Autowired
    private SB02Service sb02Service;

    @ResponseBody
    @RequestMapping(value = "/sb02/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SB02ListResponse> selectSB02Vo(@PathVariable int seq01,
        @PathVariable int parents, @PathVariable int nowPage, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb02 get");

        String userid = getSessionId(request);
        SB02ListResponse listResponse = sb02Service.selectSB02Vo(seq01, parents, nowPage, userid);

        log.debug("exit sb02 get");
        return new ResponseEntity<SB02ListResponse>(listResponse, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sb02", method = RequestMethod.POST)
    public ResponseEntity<SB02Response> insertSB02Vo(@RequestBody @Valid SB02Request sb02Request, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb02 post");

        String userid = getSessionId(request);
        SB02Response sb02Response = sb02Service.insertSB02Vo(sb02Request, userid);

        log.debug("exit sb02 post");
        return new ResponseEntity<SB02Response>(sb02Response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sb02/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
    public ResponseEntity<SB02Response> deleteSB02Vo(@PathVariable int seq,
        @PathVariable int seq01, @PathVariable int parents, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb02 delete");

		String sessionid = getSessionId(request);
        sb02Service.deleteSB02Vo(seq, seq01, parents, sessionid);

        log.debug("exit sb02 delete");
        return new ResponseEntity<SB02Response>(HttpStatus.NO_CONTENT);
    }
}
