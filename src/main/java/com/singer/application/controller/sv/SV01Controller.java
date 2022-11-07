package com.singer.application.controller.sv;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sv.SV01ListResponse;
import com.singer.application.dto.sv.SV01Request;
import com.singer.application.dto.sv.SV01Response;

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
import org.springframework.web.servlet.ModelAndView;

import com.singer.application.service.sv.SV01Service;

@Controller
public class SV01Controller extends BaseController {

    private final Log log = LogFactory.getLog(SV01Controller.class);

    @Autowired
    private SV01Service sv01Service;

    @RequestMapping(value = "/sv01/page", method = RequestMethod.GET)
    public ModelAndView showSV01() throws Exception {
        ModelAndView model = new ModelAndView("/sv01view");
        return model;
    }


    @RequestMapping(value = "/sv01/insertPage", method = RequestMethod.GET)
    public ModelAndView insertPageSV01() throws Exception {
        ModelAndView model = new ModelAndView("/sv01insert");
        return model;
    }

    @ResponseBody
    @RequestMapping(value = "/sv01/show_detail/{seq}", method = RequestMethod.GET)
    public ModelAndView selectDetailSV01Vo(@PathVariable int seq) throws Exception {
        log.debug("enter sv01show_detail get");

        ModelAndView model = new ModelAndView("/sv01view_detail");
        model.addObject("seq", seq);

        log.debug("exit sv01show_detail get");
        return model;
    }


    @ResponseBody
    @RequestMapping(value = "/sv01/{nowPage}", method = RequestMethod.GET)
    public ResponseEntity<SV01ListResponse> selectSV01Vo(@PathVariable int nowPage) throws Exception {
        log.debug("enter sv01 get");

        SV01ListResponse listResponse = sv01Service.selectSV01Vo(nowPage);

        log.debug("exit sv01 get");
        return new ResponseEntity<SV01ListResponse>(listResponse, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sv01", method = RequestMethod.POST)
    public ResponseEntity<SV01Response> insertSV01Vo(@RequestBody @Valid SV01Request sv01Request,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sv01 post");

        String userid = getSessionId(request);
        SV01Response sv01Response = sv01Service.insertSV01Vo(sv01Request, userid);

        log.debug("exit sv01 post");
        return new ResponseEntity<SV01Response>(sv01Response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sv01/one/{seq}/{recall}", method = RequestMethod.GET)
    public ResponseEntity<SV01Response> selectOneSV01Vo(@PathVariable int seq, @PathVariable int recall,
        HttpServletRequest request)
        throws Exception {
        log.debug("enter sv01One get");

        String userid = getSessionId(request);
        SV01Response response = sv01Service.selectOneSV01Vo(seq, recall, userid);

        log.debug("exit sv01One get");
        return new ResponseEntity<SV01Response>(response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sv01/{seq}", method = RequestMethod.DELETE)
    public ResponseEntity<SV01Response> deleteSV01Vo(@PathVariable int seq)
        throws Exception {
        log.debug("enter sv01 delete");

        sv01Service.deleteSV01Vo(seq);

        log.debug("exit sv01 delete");
        return new ResponseEntity<SV01Response>(HttpStatus.NO_CONTENT);
    }

    @ResponseBody
    @RequestMapping(value = "/sv01/like/{seq}", method = RequestMethod.PATCH)
    public ResponseEntity<SV01Response> likeSV01vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sv01like put");

        String sessionid = getSessionId(request);
        SV01Response sv01Response = sv01Service.likeSV01Vo(seq, sessionid);

        log.debug("exit sv01like put");
        return new ResponseEntity<SV01Response>(sv01Response, HttpStatus.OK);
    }

    @ResponseBody
    @RequestMapping(value = "/sv01/hate/{seq}", method = RequestMethod.PATCH)
    public ResponseEntity<SV01Response> hateSV01vo(@PathVariable int seq, HttpServletRequest request)
        throws Exception {
        log.debug("enter sv01hate put");

        String sessionid = getSessionId(request);
        SV01Response sv01Response = sv01Service.hateSV01Vo(seq, sessionid);

        log.debug("exit sv01hate put");
        return new ResponseEntity<SV01Response>(sv01Response, HttpStatus.OK);
    }
}
