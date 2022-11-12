package com.singer.domain.entity.sf;

import com.singer.domain.entity.ReplyEntity;
import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SF02Entity extends ReplyEntity {

	private static final long serialVersionUID = -8685145699711257079L;
	private List<SF02Entity> list;

}
