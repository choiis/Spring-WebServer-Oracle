package com.singer.domain.entity.sb;

import com.singer.common.util.Constants.YES_NO;
import com.singer.domain.entity.BoardEntity;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SB01Entity extends BoardEntity {

	private static final long serialVersionUID = -6895941086562069423L;

	private String video;

	private String videopath;

	private YES_NO videobool;

	private List<SB01Entity> list;

}
