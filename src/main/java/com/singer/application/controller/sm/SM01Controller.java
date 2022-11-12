package com.singer.application.controller.sm;

import com.singer.application.controller.BaseController;

import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sm.SM01Service;
import com.singer.domain.entity.sm.SM01Entity;

import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class SM01Controller extends BaseController {

    @Autowired
    private SM01Service sm01Service;

    @RequestMapping(value = "/sm01/joinPage", method = RequestMethod.GET)
    public ModelAndView joinPage() {

        ModelAndView model = new ModelAndView("/join");

        return model;
    }

    @RequestMapping(value = "/sm01", method = RequestMethod.POST)
    public ResponseEntity<SM01Entity> insertSM01Vo(@ModelAttribute @Valid SM01Entity sm01Vo,
        MultipartHttpServletRequest request) throws Exception {
        log.debug("enter sm01 pot");
        sm01Service.insertSM01Vo(sm01Vo, request);

        log.debug("exit sm01 post");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }

    @RequestMapping(value = "/sm01/page", method = RequestMethod.GET)
    public ModelAndView showSM01() {
        ModelAndView model = new ModelAndView("/sm01list");
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/sm01/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SM01Entity> selectSM01Vo(@ModelAttribute SM01Entity sm01Vo) throws Exception {
        log.debug("enter sm01 get");

        List<SM01Entity> list = sm01Service.selectSM01Vo(sm01Vo);
        sm01Vo.setList(list);

        log.debug("exit sm01 get");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }

    @RequestMapping(value = "/sm01/update", method = RequestMethod.POST)
    public ModelAndView upateSM01Vo(@ModelAttribute @Valid SM01Entity sm01Vo, MultipartHttpServletRequest request)
        throws Exception {
        log.debug("enter sm01update post");

        String userid = getSessionId(request);

        sm01Vo = sm01Service.updateSM01Vo(sm01Vo, request, userid);

        ModelAndView model = new ModelAndView("/sm01update");
        model.addObject("sm01Vo", sm01Vo);

        log.debug("exit sm01update post");
        return model;
    }

    @RequestMapping(value = "/sm01/change", method = RequestMethod.GET)
    public ModelAndView selectOneChangeSM01Vo(HttpServletRequest request) throws Exception {
        SM01Entity sm01Vo = new SM01Entity();
        log.debug("enter sm01change post");

        ModelAndView model = new ModelAndView("/sm01update");
        String userid = getSessionId(request);
        sm01Vo.setUserid(userid);

        sm01Vo = sm01Service.selectOneSM01Vo(sm01Vo);

        model.addObject("sm01Vo", sm01Vo);

        log.debug("exit sm01change post");
        return model;
    }

    @RequestMapping(value = "/sm01/one/{userid}", method = RequestMethod.GET)
    public ModelAndView selectOneSM01Vo(@ModelAttribute SM01Entity sm01Vo) throws Exception {
        ModelAndView model = new ModelAndView("/sm01show");
        log.debug("enter sm01One get");

        sm01Vo = sm01Service.selectOneSM01Vo(sm01Vo);
        model.addObject("sM01Vo", sm01Vo);

        log.debug("exit sm01One get");
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/sm01/photo/{userid}", method = RequestMethod.GET)
    public void selectPhotoSM01Vo(@ModelAttribute SM01Entity sm01Vo, HttpServletRequest request,
        HttpServletResponse response) throws Exception {
        log.debug("enter sm01photo get");

        @Cleanup
        InputStream is = sm01Service.selectImage(sm01Vo, request);

        IOUtils.copy(is, response.getOutputStream());

        log.debug("exit sm01photo get");
    }

    @ResponseBody
    @RequestMapping(value = "/sm01/sme1", method = RequestMethod.PUT)
    public ResponseEntity<SM01Entity> upateSME1Vo(@RequestBody SM01Entity sm01Vo, HttpServletRequest request) throws Exception {
        log.debug("enter sme1 put");

        String userid = getSessionId(request);
        sm01Service.updateSME1Vo(sm01Vo, userid);

        log.debug("exit sme1 put");
        return new ResponseEntity<SM01Entity>(sm01Vo, HttpStatus.OK);
    }

}
