package com.singer.application.controller.sm;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sm.SM02ListResponse;
import com.singer.application.dto.sm.SM02Request;
import com.singer.application.dto.sm.SM02Response;
import com.singer.application.service.sm.SM02Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sm02")
@RestController
@Slf4j
public class SM02Controller extends BaseController {

	@Autowired
	private SM02Service sm02Service;

	@ResponseBody
	@RequestMapping(value = "/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SM02ListResponse> selectSM02(@PathVariable int nowPage, HttpServletRequest request)
			throws Exception {
		log.debug("enter sm02 get");

		String userid = getSessionId(request);
		SM02ListResponse listResponse = sm02Service.selectSM02List(nowPage, userid);

		log.debug("exit sm02 get");
		return new ResponseEntity<>(listResponse, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SM02Response> deleteSM02(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sm02 delete");
		String userid = getSessionId(request);

		sm02Service.deleteSM02(seq, userid);

		log.debug("exit sm02 delete");
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<SM02Response> insertSM02(@RequestBody @Valid SM02Request sm02Request,
			HttpServletRequest request) throws Exception {
		log.debug("enter sm02 post");

		String userid = getSessionId(request);
		SM02Response response = sm02Service.insertSM02(sm02Request, userid);

		log.debug("exit sm02 post");
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

    @ResponseBody
    @RequestMapping(value = "/sm02/{seq}", method = RequestMethod.PUT)
    public ResponseEntity<Void> updateSM02(
            @PathVariable("seq") int seq,
            @Valid @RequestBody SM02Request request,
            HttpServletRequest httpRequest) throws Exception {

        log.debug("enter SM02 PUT");

        String userid = getSessionId(httpRequest); // 이미 BaseController에 있는 메서드
        sm02Service.updateSM02(seq, request, userid);

        log.debug("exit SM02 PUT");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}