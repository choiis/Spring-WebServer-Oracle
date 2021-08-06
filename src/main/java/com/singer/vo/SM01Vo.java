package com.singer.vo;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.singer.common.CommonUtil;
import com.singer.common.Constants.PHONE_INFO_CODE;
import com.singer.common.Constants.USER_CODE;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SM01Vo extends SuperVo {

	private static final long serialVersionUID = 5452779898991723254L;
	private String userid;
	private String passwd;
	private String username;
	private String brth;
	private USER_CODE grade;
	private String regdate;
	private String phone;
	private String email;
	private USER_CODE usertype;
	private MultipartFile photo;

	private String device;
	private String browser;
	private String insertid;

	private List<SM01Vo> list;

	private PHONE_INFO_CODE infocode;
	private String pfnum;
	private String pcnum;
	private String pbnum;

	private String cellpfnum;
	private String cellpcnum;
	private String cellpbnum;

	private String homepfnum;
	private String homepcnum;
	private String homepbnum;

	private String companypfnum;
	private String companypcnum;
	private String companypbnum;

	private String otherpfnum;
	private String otherpcnum;
	private String otherpbnum;

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