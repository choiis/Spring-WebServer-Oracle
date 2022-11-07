package com.singer.domain.entity.sv;

import com.singer.domain.entity.ReplyVo;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SV04Vo extends ReplyVo {

	private static final long serialVersionUID = -4273579559514540816L;
	private List<SV04Vo> list;

}
