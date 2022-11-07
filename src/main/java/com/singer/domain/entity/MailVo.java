package com.singer.domain.entity;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class MailVo implements Serializable, Cloneable {

	private static final long serialVersionUID = 6053619884340223322L;
	private String sender;
	private String email;
	private String title;
	private String contents;
	private MultipartFile file;

}
