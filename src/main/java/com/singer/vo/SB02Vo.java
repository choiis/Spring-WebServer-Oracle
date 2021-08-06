package com.singer.vo;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SB02Vo extends ReplyVo {

	private static final long serialVersionUID = 9145673639993774233L;
	private List<SB02Vo> list;

}
