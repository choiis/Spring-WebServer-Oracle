package com.singer.application.controller.sf;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sf.SF02ListResponse;
import com.singer.application.dto.sf.SF02Request;
import com.singer.application.dto.sf.SF02Response;
import com.singer.application.service.sf.SF02Service;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/sf02")
@RestController
@Slf4j
public class SF02Controller extends BaseController {
	

	@Autowired
	private SF02Service sf02Service;

	@ResponseBody
	@RequestMapping(value = "/{seq01}/{parents}/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF02ListResponse> selectSF02Vo(@PathVariable int seq01, @PathVariable int parents,
			@PathVariable int nowPage, HttpServletRequest request) throws Exception {
		log.debug("enter sf02 get");

		String userid = getSessionId(request);
		SF02ListResponse listResponse = sf02Service.selectSF02List(seq01, parents, nowPage, userid);

		log.debug("exit sf02 get");
		return new ResponseEntity<SF02ListResponse>(listResponse, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<SF02Response> insertSF02Vo(@RequestBody @Valid SF02Request sf02Request,
			HttpServletRequest request) throws Exception {
		log.debug("enter sf02 post");

		String userid = getSessionId(request);
		SF02Response sf02Response = sf02Service.insertSF02(sf02Request, userid);

		log.debug("exit sf02 post");
		return new ResponseEntity<SF02Response>(sf02Response, HttpStatus.CREATED);
	}

	@ResponseBody
	@RequestMapping(value = "/{seq}/{seq01}/{parents}", method = RequestMethod.DELETE)
	public ResponseEntity<SF02Response> deleteSF02Vo(@PathVariable int seq, @PathVariable int seq01,
			@PathVariable int parents, HttpServletRequest request) throws Exception {
		log.debug("enter sf02 delete");

		String sessionid = getSessionId(request);
		sf02Service.deleteSF02(seq, seq01, parents, sessionid);

		log.debug("exit sf02 delete");
		return new ResponseEntity<SF02Response>(HttpStatus.NO_CONTENT);
	}

}