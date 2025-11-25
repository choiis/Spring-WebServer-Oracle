package com.singer.application.controller.sb;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sb.SB01ListResponse;
import com.singer.application.dto.sb.SB01Request;
import com.singer.application.dto.sb.SB01Response;
import com.singer.application.service.sb.SB01Service;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;


import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sb01")

@RestController
@Slf4j
public class SB01Controller extends BaseController {


    @Autowired
    private SB01Service sb01Service;




    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SB01Response> insertSB01Vo(@ModelAttribute @Valid SB01Request sb01Request,
        MultipartHttpServletRequest request) throws Exception {
        log.debug("enter sb01 post");

        String userid = getSessionId(request);

        SB01Response response = sb01Service.insertSB01(sb01Request, request, userid);

        log.debug("exit sb01 post");
        return new ResponseEntity<SB01Response>(response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SB01ListResponse> selectSB01Vo(@PathVariable int nowPage) throws Exception {
        log.debug("enter sb01 get");

        SB01ListResponse listResponse = sb01Service.selectSB01List(nowPage);

        log.debug("exit sb01 get");
        return new ResponseEntity<SB01ListResponse>(listResponse, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/seq/{seq}", method = RequestMethod.GET)
    public ResponseEntity<SB01Response> selectOneSB01Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb01/seq get");

        String userid = getSessionId(request);
        SB01Response response = sb01Service.selectOneSB01(seq, userid);

        log.debug("exit sb01/seq get");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/video/{seq}", method = RequestMethod.GET)
    public void selectVideoSB01Vo(@PathVariable int seq, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        log.debug("enter sb01Video get");

        @Cleanup
        InputStream is = sb01Service.selectVideo(seq, request);

        IOUtils.copy(is, response.getOutputStream());

        log.debug("exit sb01Video get");
    }

    @ResponseBody
    @RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity<SB01Response> deleteSB01Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb01 delete");

        String sessionid = getSessionId(request);
        sb01Service.deleteSB01(seq, sessionid);

        log.debug("exit sb01 delete");
        return new ResponseEntity<SB01Response>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "/like/{seq}", method = RequestMethod.PATCH)
    public ResponseEntity<SB01Response> likeSB01Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb01like put");

        String sessionid = getSessionId(request);

        SB01Response sb01Response = sb01Service.likeSB01(seq, sessionid);

        log.debug("exit sb01like put");
        return new ResponseEntity<SB01Response>(sb01Response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/hate/{seq}", method = RequestMethod.PATCH)
    public ResponseEntity<SB01Response> hateSB01Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sb01hate put");

        String sessionid = getSessionId(request);
        SB01Response sb01Response = sb01Service.hateSB01V(seq, sessionid);

        log.debug("exit sb01hate put");
        return new ResponseEntity<SB01Response>(sb01Response, HttpStatus.OK);
    }

}