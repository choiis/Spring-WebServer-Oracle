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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.CommonUtil;
import com.singer.service.SM01Service;
import com.singer.vo.SM01Vo;

import oracle.sql.BLOB;

@Controller("sM01Controller")
public class SM01Controller {

	private final Log log = LogFactory.getLog(SM01Controller.class);

	@Resource(name = "sm01Service")
	private SM01Service sm01Service;

	@RequestMapping(value = "/sm01join.do", method = RequestMethod.GET)
	public ModelAndView joinPage() {
		log.debug("enter sm01join.do");

		ModelAndView model = new ModelAndView("/join");

		log.debug("exit sm01join.do");

		return model;
	}

	@RequestMapping(value = "/sm01insert.do", method = RequestMethod.POST)
	public ModelAndView insertSM01Vo(@ModelAttribute("SM01Vo") SM01Vo sm01Vo, MultipartHttpServletRequest request)
			throws Exception {
		log.debug("enter sm01join.do");
		log.debug("sm01Vo : " + sm01Vo);

		ModelAndView model = new ModelAndView("/index");

		sm01Service.insertSM01Vo(sm01Vo);

		MultipartFile photo = null;
		Iterator<String> itr = request.getFileNames();

		while (itr.hasNext()) {
			photo = request.getFile(itr.next());
		}

		if (!CommonUtil.chkIMGFile(photo.getName())) {

		}

		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", sm01Vo.getUserid());
		putHash.put("photo", photo.getBytes());

		sm01Service.insertImage(putHash);

		log.debug("exit sm01join.do");
		return model;
	}

	@RequestMapping(value = "/sm01page", method = RequestMethod.GET)
	public ModelAndView showSM01() {
		ModelAndView model = new ModelAndView("/sm01list");
		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/sm01select.do", method = RequestMethod.POST)
	public ResponseEntity<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception {
		log.debug("enter sm01select.do");
		log.debug("sm01Vo : " + sm01Vo);
		int nowPage = sm01Vo.getNowPage() + 1;

		List<SM01Vo> list = sm01Service.selectSM01Vo(sm01Vo);
		sm01Vo.setList(list);
		sm01Vo.setNowPage(nowPage);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			sm01Vo.setTotCnt(CommonUtil.getPageCnt(list.get(0).getTotCnt()));
		} else {
			sm01Vo.setTotCnt(0);
		}

		log.debug("list : " + list);
		log.debug("exit sm01select.do");
		return new ResponseEntity<SM01Vo>(sm01Vo, HttpStatus.OK);
	}

	@RequestMapping(value = "/sm01update.do", method = RequestMethod.POST)
	public ModelAndView upateSM01Vo(@ModelAttribute("SM01Vo") SM01Vo sm01Vo, MultipartHttpServletRequest request,
			HttpSession session) throws Exception {
		log.debug("enter sm01update.do");
		log.debug("sm01Vo : " + sm01Vo);

		sm01Vo = sm01Service.updateSM01Vo(sm01Vo);

		MultipartFile photo = null;
		Iterator<String> itr = request.getFileNames();

		while (itr.hasNext()) {
			photo = request.getFile(itr.next());
		}

		log.debug("photo.getSize() : " + photo.getSize());
		log.debug("photo.getOriginalFilename() : " + photo.getOriginalFilename());

		if (!CommonUtil.isNull(photo.getSize())) {
			HashMap<String, Object> putHash = new HashMap<String, Object>();
			putHash.put("userid", sm01Vo.getUserid());
			putHash.put("photo", photo.getBytes());

			sm01Service.insertImage(putHash);
		}

		ModelAndView model = new ModelAndView("/sm01update");

		String userid = (String) session.getAttribute("userid");

		sm01Vo.setUserid(userid);

		sm01Vo = sm01Service.selectOneSM01Vo(sm01Vo);

		model.addObject("sm01Vo", sm01Vo);

		log.debug("exit sm01update.do");
		return model;
	}

	@RequestMapping(value = "/sm01change", method = RequestMethod.GET)
	public ModelAndView selectOneSM01Vo(HttpSession session) throws Exception {
		SM01Vo sm01Vo = new SM01Vo();

		ModelAndView model = new ModelAndView("/sm01update");

		String userid = (String) session.getAttribute("userid");

		sm01Vo.setUserid(userid);

		sm01Vo = sm01Service.selectOneSM01Vo(sm01Vo);

		model.addObject("sm01Vo", sm01Vo);

		return model;
	}

	@RequestMapping(value = "/sm01show.do", method = RequestMethod.POST)
	public ModelAndView selectOneSM01Vo(@ModelAttribute("sm01Vo") SM01Vo sm01Vo) throws Exception {
		ModelAndView model = new ModelAndView("/sm01show");

		log.debug("enter sm01show.do");
		log.debug("sm01Vo : " + sm01Vo);

		sm01Vo = sm01Service.selectOneSM01Vo(sm01Vo);
		model.addObject("sM01Vo", sm01Vo);

		log.debug("sm01Vo : " + sm01Vo);
		log.debug("exit sm01show.do");

		return model;
	}

	@ResponseBody
	@RequestMapping(value = "/selectPhoto/{userid}", method = RequestMethod.GET)
	public void selectPhotoSM01Vo(@ModelAttribute SM01Vo sm01Vo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		log.debug("enter selectPhoto.do");
		log.debug("sm01Vo : " + sm01Vo);

		HashMap<String, Object> hashMap = sm01Service.selectImage(sm01Vo);

		InputStream is = null;
		try {
			// 이미지 없을때
			if (CommonUtil.isNull(hashMap)) {
				String img_path = request.getSession().getServletContext().getRealPath("/resources/img/basic.jpg");
				File file = new File(img_path);
				is = new FileInputStream(file);

				IOUtils.copy(is, response.getOutputStream());
			} else { // 이미지 불러오기 성공시
				BLOB images = (BLOB) hashMap.get("PHOTO");

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
			log.debug("exit selectPhoto.do");
		}
	}

}
