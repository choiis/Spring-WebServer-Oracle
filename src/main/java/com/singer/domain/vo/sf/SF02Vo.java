package com.singer.domain.vo.sf;

import com.singer.domain.vo.ReplyVo;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SF02Vo extends ReplyVo {

	private static final long serialVersionUID = -8685145699711257079L;
	private List<SF02Vo> list;

}
