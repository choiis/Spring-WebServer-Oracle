package com.singer.application.controller.sr;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sr.SR02Request;
import com.singer.application.dto.sr.SR02Response;
import com.singer.application.service.sr.SR02Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/sr02")

@RestController
@Slf4j
public class SR02Controller extends BaseController {


    @Autowired
    private SR02Service sr02Service;

    @ResponseBody
    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SR02Response> insertSR02Vo(@RequestBody @Valid SR02Request sr02Request,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 post");

        String userid = getSessionId(request);
        SR02Response sr02Response = sr02Service.insertSR02(sr02Request, userid);

        log.debug("exit sr02 post");
        return new ResponseEntity<SR02Response>(sr02Response, HttpStatus.CREATED);
    }

    @ResponseBody
    @RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity<SR02Response> deleteSR02Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 delete");
        sr02Service.deleteSR02(seq, getSessionId(request));

        log.debug("exit sr02 delete");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "/{seq}", method = RequestMethod.GET)
    public ResponseEntity<SR02Response> selectOneSR02Vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sr02 get");

        String userid = getSessionId(request);
        SR02Response response = sr02Service.selectOneSR02(seq, userid);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}