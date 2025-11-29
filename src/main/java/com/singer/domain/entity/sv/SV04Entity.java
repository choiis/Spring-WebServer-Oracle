package com.singer.domain.entity.sv;

import com.singer.domain.entity.ReplyEntity;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public class SV04Entity extends ReplyEntity {

	private static final long serialVersionUID = -4273579559514540816L;
	private List<SV04Entity> list;

}
