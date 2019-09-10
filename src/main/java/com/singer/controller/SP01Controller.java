package com.singer.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.service.SP01Service;
import com.singer.vo.SP01Vo;
import oracle.sql.BLOB;

@Controller("sp01Controller")
public class SP01Controller {

	private final Log log = LogFactory.getLog(SP01Controller.class);

	@Resource(name = "sp01Service")
	private SP01Service sp01Service;

	@RequestMapping(value = "/sp01page", method = RequestMethod.GET)
	public ModelAndView showSP01() throws Exception {
		ModelAndView model = new ModelAndView("/sp01view");
		return model;
	}

	@RequestMapping(value = "/sp01insertPage", method = RequestMethod.GET)
	public ModelAndView insertPageSP01() throws Exception {
		ModelAndView model = new ModelAndView("/sp01insert");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01", method = RequestMethod.POST)
	public ModelAndView insertSP01Vo(@ModelAttribute("SP01Vo") SP01Vo sp01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {

		log.debug("enter sp01insert.do");
		log.debug("SP01Vo : " + sp01Vo);

		MultipartFile explain = null;
		Iterator<String> itr = request.getFileNames();

		String userid = (String) session.getAttribute("userid");
		sp01Vo.setUserid(userid);
		String title = sp01Vo.getTitle();
		while (itr.hasNext()) {
			explain = request.getFile(itr.next());
		}

		int cnt = sp01Service.insertSP01Vo(sp01Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", userid);
		putHash.put("title", title);
		putHash.put("explain", explain.getBytes());
		int ok = sp01Service.insertExplain(putHash);

		hashmap.put("result", cnt);
		hashmap.put("ok", ok);

		ModelAndView model = new ModelAndView("/sp01view");

		log.debug("exit sp01insert.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SP01Vo> selectSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("SP01Vo : " + sp01Vo);

		List<SP01Vo> list = sp01Service.selectSP01Vo(sp01Vo);
		sp01Vo.setList(list);
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01My/{seq}", method = RequestMethod.GET)
	public ResponseEntity<SP01Vo> selectMyListSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session)
			throws Exception {

		log.debug("enter sp01My");
		log.debug("SP01Vo : " + sp01Vo);

		String userid = (String) session.getAttribute("userid");
		sp01Vo.setUserid(userid);
		List<SP01Vo> list = sp01Service.selectMyList(sp01Vo, userid);
		sp01Vo.setList(list);

		log.debug("exit sp01My");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01showFind.do", method = RequestMethod.POST)
	public ResponseEntity<SP01Vo> selectFindSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("enter sp01showFind.do");
		log.debug("SP01Vo : " + sp01Vo);

		List<SP01Vo> list = sp01Service.selectFindSP01Vo(sp01Vo);
		sp01Vo.setList(list);

		log.debug("list : " + list);
		log.debug("exit sp01showFind.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("enter sp01show_detail.do");
		log.debug("SP01Vo : " + sp01Vo);

		ModelAndView model = new ModelAndView("/sp01view_detail");
		String userid = (String) session.getAttribute("userid");
		sp01Vo = sp01Service.selectOneSP01Vo(sp01Vo, userid);
		model.addObject("sp01Vo", sp01Vo);

		log.debug("SP01Vo : " + sp01Vo);
		log.debug("exit sp01show_detail.do");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/selectExplain", method = RequestMethod.GET)
	public void selectExplainSP01Vo(@RequestParam(value = "seq") int seq, @RequestParam(value = "title") String title,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("enter selectExplain.do");

		SP01Vo sp01Vo = new SP01Vo();
		sp01Vo.setSeq(seq);
		sp01Vo.setTitle(title);

		HashMap<String, Object> hashMap = sp01Service.selectExplain(sp01Vo);

		InputStream is = null;
		try {
			// 동영상 없을때
			if (CommonUtil.isNull(hashMap)) {
				String video_path = request.getSession().getServletContext().getRealPath("/resources/video/hyeri.mp4");
				File file = new File(video_path);
				is = new FileInputStream(file);

				IOUtils.copy(is, response.getOutputStream());
			} else { // 동영상 불러오기 성공시

				BLOB images = (BLOB) hashMap.get("EXPLAIN");

				is = images.getBinaryStream();

				IOUtils.copy(is, response.getOutputStream());
			}
		} catch (IOException e) {

		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (Exception e2) {

				}
			}
			log.debug("exit selectExplain.do");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/sp01/{seq}", method = RequestMethod.DELETE)
	public ResponseEntity<SP01Vo> deleteSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01delete.do");
		log.debug("SP01Vo : " + sp01Vo);

		sp01Service.deleteSP01Vo(sp01Vo);

		sp01Vo.setResult(Constants.SUCCESS_CODE);
		log.debug("exit sp01delete.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01like/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SP01Vo> likeSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01like.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");
		sp01Vo = sp01Service.likeSP01Vo(sp01Vo, sessionid);

		log.debug("exit sp01like.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01hate/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SP01Vo> hateSP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01hate.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");

		sp01Vo = sp01Service.hateSP01Vo(sp01Vo, sessionid);

		log.debug("exit sp01hate.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01buy/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SP01Vo> buySP01Vo(@ModelAttribute SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01buy.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sp01Service.buySP01Vo(sp01Vo, sessionid);

		sp01Vo.setResult(Constants.SUCCESS_CODE);
		sp01Vo.setLike(like);
		log.debug("exit sp01buy.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01sell/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SP01Vo> sellSP01Vo(@ModelAttribute SP01Vo sp01Vo) throws Exception {
		log.debug("enter sp01sell.do");
		log.debug("SP01Vo : " + sp01Vo);
		int like = sp01Service.sellSP01Vo(sp01Vo);

		sp01Vo.setResult(Constants.SUCCESS_CODE);
		sp01Vo.setLike(like);
		log.debug("exit sp01sell.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sp01cancel/{seq}", method = RequestMethod.PUT)
	public ResponseEntity<SP01Vo> cancelSP01Vo(@ModelAttribute SP01Vo sp01Vo) throws Exception {
		log.debug("enter sp01cancel.do");
		log.debug("SP01Vo : " + sp01Vo);
		int like = sp01Service.cancelSP01Vo(sp01Vo);

		sp01Vo.setResult(Constants.SUCCESS_CODE);
		sp01Vo.setLike(like);
		log.debug("exit sp01cancel.do");
		return new ResponseEntity<SP01Vo>(sp01Vo, HttpStatus.OK);
	}
}