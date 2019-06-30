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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.service.SB01Service;
import com.singer.vo.SB01Vo;

import oracle.sql.BLOB;

@Controller("sB01Controller")
public class SB01Controller {

	private final Log log = LogFactory.getLog(SB01Controller.class);

	@Resource(name = "sb01Service")
	private SB01Service sb01Service;

	@RequestMapping(value = "/sb01.do", method = RequestMethod.GET)
	public ModelAndView showSB01() throws Exception {
		ModelAndView model = new ModelAndView("/sb01view");
		log.debug("enter sb01.do");

		log.debug("exit sb01.do");
		return model;
	}

	@RequestMapping(value = "/sb01write.do", method = RequestMethod.GET)
	public ModelAndView writeSB01() throws Exception {
		ModelAndView model = new ModelAndView("/sb01insert");
		log.debug("enter sb01write.do");

		log.debug("exit sb01write.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01insert.do", method = RequestMethod.POST)
	public ModelAndView insertSB01Vo(@ModelAttribute("SB01Vo") SB01Vo sb01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {

		log.debug("enter sb01insert.do");
		log.debug("sb01Vo : " + sb01Vo);

		MultipartFile video = null;
		Iterator<String> itr = request.getFileNames();

		String userid = (String) session.getAttribute("userid");
		sb01Vo.setUserid(userid);
		String title = sb01Vo.getTitle();
		while (itr.hasNext()) {
			video = request.getFile(itr.next());
		}

		int cnt = sb01Service.insertSB01Vo(sb01Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", userid);
		putHash.put("title", title);
		putHash.put("video", video.getBytes());
		int ok = sb01Service.insertVideo(putHash);

		hashmap.put("result", cnt);
		hashmap.put("ok", ok);

		ModelAndView model = new ModelAndView("/sb01view");

		log.debug("exit sb01insert.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01show.do", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> selectSB01Vo(SB01Vo sb01Vo, HttpSession session) throws Exception {

		log.debug("enter sb01show.do");
		log.debug("sb01Vo : " + sb01Vo);
		int nowPage = sb01Vo.getNowPage() + 1;

		List<SB01Vo> list = sb01Service.selectSB01Vo(sb01Vo);
		sb01Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sb01Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			sb01Vo.setTotCnt(0);
		}
		sb01Vo.setNowPage(nowPage);
		log.debug("list : " + list);
		log.debug("exit sb01show.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01showFind.do", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo, HttpSession session) throws Exception {

		log.debug("enter sb01showFind.do");
		log.debug("sb01Vo : " + sb01Vo);

		List<SB01Vo> list = sb01Service.selectFindSB01Vo(sb01Vo);
		sb01Vo.setList(list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sb01Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			sb01Vo.setTotCnt(0);
		}
		log.debug("list : " + list);
		log.debug("exit sb01showFind.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01show_detail/{seq}", method = RequestMethod.GET)
	public ModelAndView selectOneSB01Vo(@PathVariable("seq") int seq, HttpSession session) throws Exception {
		SB01Vo sb01Vo = new SB01Vo();
		sb01Vo.setSeq(seq);
		log.debug("enter sb01show_detail");
		log.debug("sb01Vo : " + sb01Vo);

		ModelAndView model = new ModelAndView("/sb01view_detail");
		String userid = (String) session.getAttribute("userid");
		sb01Vo = sb01Service.selectOneSB01Vo(sb01Vo, userid);
		model.addObject("sb01Vo", sb01Vo);

		log.debug("sb01Vo : " + sb01Vo);
		log.debug("exit sb01show_detail");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/selectVideo", method = RequestMethod.GET)
	public void selectPhotoSB01Vo(@RequestParam(value = "seq") int seq, @RequestParam(value = "title") String title,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		log.debug("enter selectVideo.do");

		SB01Vo sb01Vo = new SB01Vo();
		sb01Vo.setSeq(seq);
		sb01Vo.setTitle(title);

		HashMap<String, Object> hashMap = sb01Service.selectVideo(sb01Vo);

		InputStream is = null;
		try {
			// 동영상 없을때
			if (CommonUtil.isNull(hashMap)) {
				String video_path = request.getSession().getServletContext().getRealPath("/resources/video/hyeri.mp4");
				File file = new File(video_path);
				is = new FileInputStream(file);

				IOUtils.copy(is, response.getOutputStream());
			} else { // 동영상 불러오기 성공시

				BLOB images = (BLOB) hashMap.get("VIDEO");

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
			log.debug("exit selectVideo.do");
		}
	}

	@ResponseBody
	@RequestMapping(value = "/sb01delete.do", method = RequestMethod.POST)
	public ModelAndView deleteSB01Vo(@ModelAttribute("SB01Vo") SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01delete.do");
		log.debug("sb01Vo : " + sb01Vo);

		sb01Service.deleteSB01Vo(sb01Vo);

		ModelAndView model = new ModelAndView("/sb01view");
		log.debug("exit sb01delete.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sb01like.do", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> likeSB01Vo(SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01like.do");
		log.debug("sb01Vo : " + sb01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sb01Service.likeSB01Vo(sb01Vo, sessionid);

		sb01Vo.setResult(Constants.SUCCESS_CODE);
		sb01Vo.setLike(like);
		log.debug("exit sb01like.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}

	@ResponseBody
	@RequestMapping(value = "/sb01hate.do", method = RequestMethod.POST)
	public ResponseEntity<SB01Vo> hateSB01Vo(SB01Vo sb01Vo, HttpSession session) throws Exception {
		log.debug("enter sb01hate.do");
		log.debug("sb01Vo : " + sb01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sb01Service.hateSB01Vo(sb01Vo, sessionid);

		sb01Vo.setResult(Constants.SUCCESS_CODE);
		sb01Vo.setLike(like);
		log.debug("exit sb01hate.do");
		return new ResponseEntity<SB01Vo>(sb01Vo, HttpStatus.OK);
	}
}