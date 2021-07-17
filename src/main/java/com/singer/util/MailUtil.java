package com.singer.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeMessage.RecipientType;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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

		if (StringUtils.isEmpty(mailVo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);

		}
		if (StringUtils.isEmpty(mailVo.getContents())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		InputStream is = null;
		File file = null;
		FileOutputStream fos = null;
		ResourcePropertySource resourse;

		try {
			MimeMessage message = mailSender.createMimeMessage();
			StringBuilder setfrom = new StringBuilder(mailVo.getSender());
			setfrom.append("@company.co.kr");
			message.setFrom(new InternetAddress(setfrom.toString())); // 보내는사람 생략하거나 하면 정상작동을 안함

			message.addRecipient(RecipientType.TO, new InternetAddress(mailVo.getEmail())); // 받는사람
			// 이메일
			message.setSubject(mailVo.getTitle()); // 메일제목은 생략이 가능하다
			message.setText(mailVo.getContents(), "utf-8", "html"); // 메일 내용

			if (!ObjectUtils.isEmpty(mailVo.getFile()) && mailVo.getFile().getSize() != 0) {
				String fileName = new String(mailVo.getFile().getOriginalFilename().getBytes("KSC5601"), "8859_1");
				is = mailVo.getFile().getInputStream();
				resourse = new ResourcePropertySource(new ClassPathResource("conf/property/global.properties"));
				StringBuilder path = new StringBuilder((String) resourse.getProperty("global.mailFile.path"));
				path.append("/");
				path.append(fileName);
				file = new File(path.toString());
				fos = new FileOutputStream(path.toString());
				int read = 0;

				byte[] bytes = new byte[1024];

				while ((read = is.read(bytes)) != -1) {
					fos.write(bytes, 0, read);
				}

				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				FileDataSource dataSource = new FileDataSource(file);
				mimeBodyPart.setDataHandler(new DataHandler(dataSource));
				mimeBodyPart.setFileName(fileName);

				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(mimeBodyPart);
				message.setContent(multipart);

			}
			mailSender.send(message);
		} catch (IOException e) {
			log.debug("MAIL FAIL " + e.getLocalizedMessage());
			return RESULT_CODE.FAIL.getValue();
		} catch (AddressException e) {
			log.debug("MAIL FAIL " + e.getLocalizedMessage());
			return RESULT_CODE.FAIL.getValue();
		} catch (MessagingException e) {
			log.debug("MAIL FAIL " + e.getLocalizedMessage());
			return RESULT_CODE.FAIL.getValue();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (fos != null) {
					fos.close();
				}
				if (file != null) {
					file.delete();
				}
			} catch (IOException e) {
				log.debug("Resource delete" + e.getLocalizedMessage());
			}

		}

		log.debug("MAIL SUCCESS");

		return RESULT_CODE.SUCCESS.getValue();
	}
}
