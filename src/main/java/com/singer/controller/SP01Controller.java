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
import com.singer.service.SP01Service;
import com.singer.vo.SP01Vo;
import oracle.sql.BLOB;

@Controller("sp01Controller")
public class SP01Controller {

	private final Log log = LogFactory.getLog(SP01Controller.class);

	@Resource(name = "sp01Service")
	private SP01Service sp01Service;

	@RequestMapping(value = "/sp01.do", method = RequestMethod.GET)
	public ModelAndView toShowSP01() throws Exception {
		ModelAndView model = new ModelAndView("/sp01view");
		log.debug("enter sp01.do");

		log.debug("exit sp01.do");
		return model;
	}

	@RequestMapping(value = "/sp01write.do", method = RequestMethod.GET)
	public ModelAndView toInsertSP01() throws Exception {
		ModelAndView model = new ModelAndView("/sp01insert");
		log.debug("enter sp01write.do");

		log.debug("exit sp01write.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01insert.do", method = RequestMethod.POST)
	public ModelAndView toInsertSP01Vo(@ModelAttribute("SP01Vo") SP01Vo sp01Vo, HttpSession session,
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
	@RequestMapping(value = "/sp01show.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("enter sp01show.do");
		log.debug("SP01Vo : " + sp01Vo);
		int nowPage = sp01Vo.getNowPage() + 1;
		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		List<SP01Vo> list = sp01Service.selectSP01Vo(sp01Vo);
		hashmap.put("list", list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}
		hashmap.put("nowPage", nowPage);
		log.debug("list : " + list);
		log.debug("exit sp01show.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01showMyList.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectMyListSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("enter sp01showMyList.do");
		log.debug("SP01Vo : " + sp01Vo);
		int nowPage = sp01Vo.getNowPage() + 1;
		HashMap<String, Object> hashmap = new HashMap<String, Object>();
		String userid = (String) session.getAttribute("userid");
		sp01Vo.setUserid(userid);
		List<SP01Vo> list = sp01Service.selectMyList(sp01Vo, userid);
		hashmap.put("list", list);

		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}
		hashmap.put("nowPage", nowPage);
		log.debug("list : " + list);
		log.debug("exit sp01showMyList.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01showFind.do", method = RequestMethod.POST)
	public HashMap<String, Object> toSelectFindSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {

		log.debug("enter sp01showFind.do");
		log.debug("SP01Vo : " + sp01Vo);

		HashMap<String, Object> hashmap = new HashMap<String, Object>();

		List<SP01Vo> list = sp01Service.selectFindSP01Vo(sp01Vo);
		hashmap.put("list", list);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			hashmap.put("size", CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			hashmap.put("size", 0);
		}
		log.debug("list : " + list);
		log.debug("exit sp01showFind.do");
		return hashmap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01show_detail.do", method = RequestMethod.GET)
	public ModelAndView toSelectOneSP01Vo(@ModelAttribute("SP01Vo") SP01Vo sp01Vo, HttpSession session)
			throws Exception {

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
	public void toSelectExplainSP01Vo(@RequestParam(value = "seq") int seq, @RequestParam(value = "title") String title,
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
	@RequestMapping(value = "/sp01delete.do", method = RequestMethod.POST)
	public ModelAndView toDeleteSP01Vo(@ModelAttribute("SP01Vo") SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01delete.do");
		log.debug("SP01Vo : " + sp01Vo);

		sp01Service.deleteSP01Vo(sp01Vo);

		ModelAndView model = new ModelAndView("/sp01view");
		log.debug("exit sp01delete.do");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01like.do", method = RequestMethod.POST)
	public HashMap<String, Object> likeSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01like.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sp01Service.likeSP01Vo(sp01Vo, sessionid);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sp01like.do");
		return hashMap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01hate.do", method = RequestMethod.POST)
	public HashMap<String, Object> hateSP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01hate.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sp01Service.hateSP01Vo(sp01Vo, sessionid);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sp01hate.do");
		return hashMap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01buy.do", method = RequestMethod.POST)
	public HashMap<String, Object> buySP01Vo(SP01Vo sp01Vo, HttpSession session) throws Exception {
		log.debug("enter sp01buy.do");
		log.debug("SP01Vo : " + sp01Vo);
		String sessionid = (String) session.getAttribute("userid");
		int like = sp01Service.buySP01Vo(sp01Vo, sessionid);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sp01buy.do");
		return hashMap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01sell.do", method = RequestMethod.POST)
	public HashMap<String, Object> sellSP01Vo(SP01Vo sp01Vo) throws Exception {
		log.debug("enter sp01sell.do");
		log.debug("SP01Vo : " + sp01Vo);
		int like = sp01Service.sellSP01Vo(sp01Vo);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sp01sell.do");
		return hashMap;
	}

	@ResponseBody
	@RequestMapping(value = "/sp01cancel.do", method = RequestMethod.POST)
	public HashMap<String, Object> cancelSP01Vo(SP01Vo sp01Vo) throws Exception {
		log.debug("enter sp01cancel.do");
		log.debug("SP01Vo : " + sp01Vo);
		int like = sp01Service.cancelSP01Vo(sp01Vo);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		hashMap.put("result", 1);
		hashMap.put("like", like);
		log.debug("exit sp01cancel.do");
		return hashMap;
	}
}