package com.singer.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MailVo implements Serializable {

	private static final long serialVersionUID = -6476594188315547364L;
	private String sender;
	private String email;
	private String title;
	private String contents;
	private MultipartFile file;

}
