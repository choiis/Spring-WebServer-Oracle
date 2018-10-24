package com.singer.controller;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import com.singer.service.SF01Service;
import com.singer.vo.SF01Vo;
import oracle.sql.BLOB;

@Controller("sF01Controller")
public class SF01Controller {

	private final Log log = LogFactory.getLog(SF01Controller.class);

	@Resource(name = "sf01Service")
	private SF01Service sf01Service;

	@RequestMapping(value = "/sf01.do", method = RequestMethod.GET)
	public ModelAndView toShowSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01view");
		log.debug("enter sf01.do");

		log.debug("exit sf01.do");
		return model;
	}

	@RequestMapping(value = "/sf01write.do", method = RequestMethod.GET)
	public ModelAndView toInsertSF01() throws Exception {
		ModelAndView model = new ModelAndView("/sf01insert");
		log.debug("enter sf01write.do");

		log.debug("exit sf01write.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01insert.do", method = RequestMethod.POST)
	public ModelAndView toInsertSF01Vo(@ModelAttribute("SF01Vo") SF01Vo sf01Vo, HttpSession session,
			MultipartHttpServletRequest request) throws Exception {
		log.debug("enter sf01insert.do");
		log.debug("sf01Vo : " + sf01Vo);

		MultipartFile file = null;
		Iterator<String> itr = request.getFileNames();

		String userid = (String) session.getAttribute("userid");
		sf01Vo.setUserid(userid);
		String title = sf01Vo.getTitle();
		while (itr.hasNext()) {
			file = request.getFile(itr.next());
		}

		sf01Vo.setFilename(file.getOriginalFilename());

		int cnt = sf01Service.insertSF01Vo(sf01Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", userid);
		putHash.put("title", title);

		putHash.put("fileblob", file.getBytes());
		int ok = sf01Service.insertFile(putHash);

		hashmap.put("result", cnt);
		hashmap.put("ok", ok);

		ModelAndView model = new ModelAndView("/sf01view");
		log.debug("exit sf01insert.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSF01Vo(SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01show.do");
		log.debug("sf01Vo : " + sf01Vo);
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		int nowPage = sf01Vo.getNowPage() + 1;
		List<SF01Vo> list = sf01Service.selectSF01Vo(sf01Vo);
		hashmap.put("list", list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}
		hashmap.put("nowPage", nowPage);
		log.debug("list : " + list);
		log.debug("exit sf01show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01showFind.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectFindSF01Vo(SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01showFind.do");
		log.debug("sf01Vo : " + sf01Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		List<SF01Vo> list = sf01Service.selectFindSF01Vo(sf01Vo);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}
		log.debug("list : " + list);
		log.debug("exit sf01showFind.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01show_detail.do", method = RequestMethod.GET)
	public ModelAndView toSelectOneSF01Vo(@ModelAttribute("SF01Vo") SF01Vo sf01Vo, HttpSession session)
			throws Exception {
		log.debug("enter sf01show_detail.do");
		log.debug("sf01Vo : " + sf01Vo);

		ModelAndView model = new ModelAndView("/sf01view_detail");
		String userid = (String) session.getAttribute("userid");
		sf01Vo = sf01Service.selectOneSF01Vo(sf01Vo, userid);
		model.addObject("sf01Vo", sf01Vo);

		log.debug("sf01Vo : " + sf01Vo);
		log.debug("exit sf01showFind.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/selectFile.do", method = RequestMethod.GET)
	public void toSelectPhotoSF01Vo(@RequestParam(value = "seq") int seq,
			@RequestParam(value = "regdate") String regdate, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		log.debug("enter selectFile.do");

		SF01Vo sf01Vo = new SF01Vo();
		sf01Vo.setSeq(seq);
		sf01Vo.setRegdate(regdate);
		log.debug("sf01Vo : " + sf01Vo);
		HashMap<String, Object> hashMap = sf01Service.selectFile(sf01Vo);

		String filename = (String) hashMap.get("FILENAME");
		BLOB fileblob = (BLOB) hashMap.get("FILEBLOB");

		byte[] fileBytes = fileblob.getBytes();
		try {

			response.setContentType("application/octet-stream");
			response.setContentLength(fileBytes.length);
			response.setHeader("Content-Disposition",
					"attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.getOutputStream().write(fileBytes);

			response.getOutputStream().flush();
			response.getOutputStream().close();

		} finally {
			response.getOutputStream().close();
			log.debug("exit selectFile.do");
		}

	}

	@ResponseBody
	@RequestMapping(value = "/sf01delete.do", method = RequestMethod.POST)
	public ModelAndView toDeleteSF01Vo(@ModelAttribute("SF01Vo") SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01delete.do");
		log.debug("sf01Vo : " + sf01Vo);

		sf01Service.deleteSF01Vo(sf01Vo);

		ModelAndView model = new ModelAndView("/sf01view");

		log.debug("exit sf01delete.do");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01like.do", method = RequestMethod.POST)
	public HashMap<String, Object> likeSF01Vo(SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01like.do");
		log.debug("sf01Vo : " + sf01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sf01Service.likeSF01Vo(sf01Vo, sessionid);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sf01like.do");
		return hashMap;
	}

	@ResponseBody
	@RequestMapping(value = "/sf01hate.do", method = RequestMethod.POST)
	public HashMap<String, Object> hateSF01Vo(SF01Vo sf01Vo, HttpSession session) throws Exception {
		log.debug("enter sf01hate.do");
		log.debug("sf01Vo : " + sf01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sf01Service.hateSF01Vo(sf01Vo, sessionid);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sf01hate.do");
		return hashMap;
	}
}