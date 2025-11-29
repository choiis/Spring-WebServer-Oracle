package com.singer.application.controller.sr;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sr.SR03ListResponse;
import com.singer.application.dto.sr.SR03Request;
import com.singer.application.dto.sr.SR03Response;
import com.singer.application.service.sr.SR03Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sr03")
@RestController
@Slf4j
public class SR03Controller extends BaseController {

    @Autowired
    private SR03Service sr03Service;

    @ResponseBody
    @RequestMapping(value = "/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SR03ListResponse> selectsr03Vo(@PathVariable int seq01,
        @PathVariable int parents, @PathVariable int nowPage, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr03 get");

        String userid = getSessionId(request);
        SR03ListResponse listResponse = sr03Service.selectSR03List(seq01, parents, nowPage, userid);

        log.debug("exit sr03 get");
        return new ResponseEntity<SR03ListResponse>(listResponse, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SR03Response> insertsr03Vo(@RequestBody @Valid SR03Request sr03Request, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr03 post");

        String userid = getSessionId(request);
        SR03Response sr03Response = sr03Service.insertSR03(sr03Request, userid);

        log.debug("exit sr03 post");
        return new ResponseEntity<SR03Response>(sr03Response, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
    public ResponseEntity<SR03Response> deletesr03Vo(@PathVariable int seq,
        @PathVariable int seq01, @PathVariable int parents, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr03 delete");

		String sessionid = getSessionId(request);
        sr03Service.deleteSR03(seq, seq01, parents, sessionid);

        log.debug("exit sr03 delete");
        return new ResponseEntity<SR03Response>(HttpStatus.NO_CONTENT);
    }
}