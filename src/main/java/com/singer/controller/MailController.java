package com.singer.controller;

import java.util.Iterator;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.CommonUtil;
import com.singer.exception.AppException;
import com.singer.util.MailUtil;
import com.singer.vo.MailVo;

@Controller("mailController")
public class MailController {

	@Autowired
	private MailUtil mailUtil;

	private final Log log = LogFactory.getLog(MailController.class);

	@ResponseBody
	@RequestMapping(value = "/sendMail", method = RequestMethod.POST)
	public int sendMail(@ModelAttribute MailVo mailVo, MultipartHttpServletRequest request, HttpSession session)
			throws AppException {

		String userid = (String) session.getAttribute("userid");
		mailVo.setSender(userid);
		MultipartFile file = null;
		Iterator<String> itr = request.getFileNames();
		while (itr.hasNext()) {
			file = request.getFile(itr.next());
		}
		if (!CommonUtil.isNull(file)) {
			mailVo.setFile(file);
		}
		log.debug("sendMail " + mailVo);
		return mailUtil.mailSend(mailVo);

	}
}
