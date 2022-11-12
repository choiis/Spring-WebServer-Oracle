package com.singer.domain.entity.sv;

import com.singer.domain.entity.SuperEntity;
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
public class SV02Entity extends SuperEntity {

	private static final long serialVersionUID = 1769936264551524793L;
	private int seq;
	private int idx;
	private String content;
	private String regdate;
	private String userid;
	private int voted;
	private int totVoted;
	private int votedYn;
	private List<SV02Entity> sv02Vos;

}
