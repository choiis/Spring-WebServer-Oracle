package com.singer.domain.vo.sv;

import com.singer.domain.vo.SuperVo;
import java.util.List;

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
public class SV02Vo extends SuperVo {

	private static final long serialVersionUID = 1769936264551524793L;
	private int seq;
	private int idx;
	private String content;
	private String regdate;
	private String userid;
	private int voted;
	private int totVoted;
	private int votedYn;
	private List<SV02Vo> sv02Vos;

}
