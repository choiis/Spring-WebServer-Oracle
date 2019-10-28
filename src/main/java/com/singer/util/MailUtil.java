package com.singer.util;

import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import com.singer.common.CommonUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.vo.MailVo;

@Component("mailUtil")
public class MailUtil {

	@Autowired
	private JavaMailSenderImpl mailSender;

	private final Log log = LogFactory.getLog(MailUtil.class);

	public int mailSend(MailVo mailVo) throws AppException {

		if (CommonUtil.isNull(mailVo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);

		}
		if (CommonUtil.isNull(mailVo.getContents())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		try {
			MimeMessage message = mailSender.createMimeMessage();
			StringBuilder setfrom = new StringBuilder(mailVo.getSender());
			setfrom.append("@company.co.kr");
			message.setFrom(new InternetAddress(setfrom.toString())); // 보내는사람 생략하거나 하면 정상작동을 안함

			message.addRecipient(RecipientType.TO, new InternetAddress(mailVo.getEmail())); // 받는사람
			// 이메일
			message.setSubject(mailVo.getTitle()); // 메일제목은 생략이 가능하다
			message.setText(mailVo.getContents(), "utf-8", "html"); // 메일 내용
			mailSender.send(message);

		} catch (Exception e) {
			log.debug("MAIL FAIL " + e.getLocalizedMessage());
			return RESULT_CODE.FAIL.getValue();
		}
		log.debug("MAIL SUCCESS");

		return RESULT_CODE.SUCCESS.getValue();
	}
}
