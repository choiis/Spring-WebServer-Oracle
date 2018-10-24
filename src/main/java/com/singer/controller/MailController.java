package com.singer.controller;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.singer.common.Constants;
import com.singer.vo.MailVo;

@Controller("mailController")
public class MailController {

	@Autowired
	private JavaMailSenderImpl mailSender;

	private final Log log = LogFactory.getLog(MailController.class);

	@ResponseBody
	@RequestMapping(value = "/sendMail.do", method = RequestMethod.POST)
	public int sendMail(ModelAndView modelAndView, MailVo mailVo, HttpServletResponse response) {
		log.debug("enter sendMail.do");
		log.debug("mailVo : " + mailVo);

		String setfrom = "choiis120@naver.com";

		try {
			MimeMessage message = mailSender.createMimeMessage();

			message.setFrom(new InternetAddress(setfrom)); // 보내는사람 생략하거나 하면
															// 정상작동을 안함
			message.addRecipient(RecipientType.TO, new InternetAddress(mailVo.getEmail())); // 받는사람
			// 이메일
			message.setSubject(mailVo.getTitle()); // 메일제목은 생략이 가능하다
			message.setText(mailVo.getContents(), "utf-8", "html"); // 메일 내용
			mailSender.send(message);

		} catch (Exception e) {
			log.debug("MAIL FAIL");
			log.debug("exit sendMail.do");
			return Constants.ERROR_LOGIN_FAIL;
		}
		log.debug("MAIL SUCCESS");
		log.debug("exit sendMail.do");
		return Constants.SUCCESS_CODE;
	}
}
