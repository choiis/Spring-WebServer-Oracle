package com.singer.application.controller.sf;

import com.singer.application.controller.BaseController;
import com.singer.application.dto.sf.SF01ListResponse;
import com.singer.application.dto.sf.SF01Request;
import com.singer.application.dto.sf.SF01Response;
import com.singer.application.service.sf.SF01Service;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/sf01")

@RestController
@Slf4j
public class SF01Controller extends BaseController {

	@Autowired
	private SF01Service sf01Service;




	@ResponseBody
	@RequestMapping(value = "", method = RequestMethod.POST)
	public ResponseEntity<SF01Response> insertSF01Vo(@ModelAttribute @Valid SF01Request sf01Request,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sf01 post");

		String userid = getSessionId(request);

		SF01Response sf01Response = sf01Service.insertSF01(sf01Request, request, userid);

		log.debug("exit sf01 post");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/{nowPage}", method = RequestMethod.GET)
	public ResponseEntity<SF01ListResponse> selectSF01Vo(@PathVariable int nowPage) throws Exception {
		log.debug("enter sf01 get");

		SF01ListResponse listResponse = sf01Service.selectSF01List(nowPage);

		log.debug("exit sf01 get");
		return new ResponseEntity<SF01ListResponse>(listResponse, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/seq/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SF01Response> selectOneSF01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter /sf01/seq get");

		String userid = getSessionId(request);
		SF01Response sf01Response = sf01Service.selectOneSF01(seq, userid);

		log.debug("exit /sf01/seq get");
		return new ResponseEntity<>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/file/{seq}", method = RequestMethod.GET)
	public ResponseEntity<Object> selectFileSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01File get");

		String userid = getSessionId(request);
		File file = sf01Service.selectFile(seq, userid);
		Resource resource = new InputStreamResource(Files.newInputStream(Paths.get(file.getPath())));

		HttpHeaders headers = new HttpHeaders();
		headers.setContentDisposition(ContentDisposition.builder("attachment").filename(file.getName()).build());

		log.debug("exit sf01File get");
		return new ResponseEntity<Object>(resource, headers, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SF01Response> deleteSF01Vo(@PathVariable int seq, HttpServletRequest request)
			throws Exception {
		log.debug("enter sf01 delete");

		String sessionid = getSessionId(request);
		sf01Service.deleteSF01(seq, sessionid);

		log.debug("exit sf01 delete");
		return new ResponseEntity<SF01Response>(HttpStatus.NO_CONTENT);
	}

	@ResponseBody
	@RequestMapping(value = "/like/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Response> likeSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01like put");

		String sessionid = getSessionId(request);

		SF01Response sf01Response = sf01Service.likeSF01(seq, sessionid);

		log.debug("exit sf01like put");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/hate/{seq}", method = RequestMethod.PATCH)
	public ResponseEntity<SF01Response> hateSF01Vo(@PathVariable int seq, HttpServletRequest request) throws Exception {
		log.debug("enter sf01hate put");

		String sessionid = getSessionId(request);

		SF01Response sf01Response = sf01Service.hateSF01(seq, sessionid);

		log.debug("exit sf01hate put");
		return new ResponseEntity<SF01Response>(sf01Response, HttpStatus.OK);
	}
}