package com.singer.domain.entity.sv;

import com.singer.domain.entity.BoardEntity;
import java.util.List;

import com.singer.common.util.CommonUtil;

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
public class SV01Entity extends BoardEntity {

	private static final long serialVersionUID = -3009192462921744979L;
	private int recall;
	private int votedCnt;
	private int multiselect;

	private List<SV01Entity> list;
	private List<SV02Entity> sv02Vos;

	private int votedYn;

	public void setList(List<SV01Entity> list) {
		this.list = list;
		// �럹�씠吏뺤쓣 �쐞�븳 移댁슫�듃
		if (this.list.size() != 0) {
			super.setTotCnt(CommonUtil.getPageCnt(this.list.get(0).getTotCnt()));
		} else {
			super.setTotCnt(0);
		}
	}

}
