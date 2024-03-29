package com.singer.domain.entity.sr;

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
public class SR03Entity extends ReplyEntity {

	private static final long serialVersionUID = 5820196340241578631L;
	private List<SR03Entity> list;

}
