package com.singer.application.controller.sm;

import com.singer.application.controller.BaseController;
import com.singer.application.service.sm.SM01Service;
import com.singer.domain.entity.sm.SM01Entity;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.InputStream;
import java.util.List;

@RequestMapping("/api/sm01")

@RestController
@Slf4j
public class SM01Controller extends BaseController {

    @Autowired
    private SM01Service sm01Service;


    @RequestMapping(value = "", method = RequestMethod.POST)
    public ResponseEntity<SM01Entity> insertSM01Vo(@ModelAttribute @Valid SM01Entity sm01Vo,
        MultipartHttpServletRequest request) throws Exception {
        log.debug("enter sm01 pot");
        sm01Service.insertSM01Vo(sm01Vo, request);

        log.debug("exit sm01 post");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SM01Entity> selectSM01Vo(@ModelAttribute SM01Entity sm01Vo) throws Exception {
        log.debug("enter sm01 get");

        List<SM01Entity> list = sm01Service.selectSM01Vo(sm01Vo);
        sm01Vo.setList(list);

        log.debug("exit sm01 get");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }




    @ResponseBody
    @RequestMapping(value = "/photo/{userid}", method = RequestMethod.GET)
    public void selectPhotoSM01Vo(@ModelAttribute SM01Entity sm01Vo, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        log.debug("enter sm01photo get");

        @Cleanup
        InputStream is = sm01Service.selectImage(sm01Vo, request);

        IOUtils.copy(is, response.getOutputStream());

        log.debug("exit sm01photo get");
    }

    @ResponseBody
    @RequestMapping(value = "/sme1", method = RequestMethod.PUT)
    public ResponseEntity<SM01Entity> upateSME1Vo(@RequestBody SM01Entity sm01Vo, HttpServletRequest request) throws Exception {
        log.debug("enter sme1 put");

        String userid = getSessionId(request);
        sm01Service.updateSME1Vo(sm01Vo, userid);

        log.debug("exit sme1 put");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }

}