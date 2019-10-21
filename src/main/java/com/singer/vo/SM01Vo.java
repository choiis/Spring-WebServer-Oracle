package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.singer.common.CommonUtil;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
public class SM01Vo extends SuperVo {

	private String userid;
	private String passwd;
	private String username;
	private String brth;
	private int grade;
	private String regdate;
	private String phone;
	private String email;
	private int usertype;
	private MultipartFile photo;

	private String device;
	private String browser;

	private List<SM01Vo> list;

	public void setList(List<SM01Vo> list) {
		this.list = list;
		super.setNowPage(super.getNowPage() + 1);
		// 페이징을 위한 카운트
		if (list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}