package com.singer.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class MailVo implements Serializable, Cloneable {

	private static final long serialVersionUID = 6053619884340223322L;
	private String sender;
	private String email;
	private String title;
	private String contents;
	private MultipartFile file;

}
