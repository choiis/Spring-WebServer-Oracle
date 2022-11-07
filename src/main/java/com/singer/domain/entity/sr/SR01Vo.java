package com.singer.domain.entity.sr;

import com.singer.domain.entity.BoardVo;
import java.util.List;

import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Range;
import org.springframework.web.multipart.MultipartFile;

import com.singer.common.util.CommonUtil;
import com.singer.common.exception.ExceptionMsg;

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
public class SR01Vo extends BoardVo {

	private static final long serialVersionUID = -460762083171126264L;

	@NotEmpty(message = ExceptionMsg.EXT_MSG_INPUT_6)
	private String markertitle;
	private double mapx;
	private double mapy;
	private MultipartFile photo;
	private String photopath;

	@Range(min = 0, max = 5, message = ExceptionMsg.EXT_MSG_INPUT_7)
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
