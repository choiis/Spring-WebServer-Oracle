package com.singer.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class MailVo {

	private String sender;
	private String email;
	private String title;
	private String contents;
	private MultipartFile file;

}
