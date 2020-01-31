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
public class SR01Vo extends BoardVo {

	private String markertitle;
	private double mapx;
	private double mapy;
	private MultipartFile photo;

	private double grade;
	private double avggrade;
	private int idx;
	private int photocnt;

	private List<SR01Vo> list;

	public void setList(List<SR01Vo> list) {
		this.list = list;
		// 페이징을 위한 카운트
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
