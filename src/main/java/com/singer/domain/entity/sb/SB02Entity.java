package com.singer.domain.entity.sb;

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
public class SB02Entity extends ReplyEntity {

	private static final long serialVersionUID = 9145673639993774233L;
	private List<SB02Entity> list;

}
