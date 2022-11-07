package com.singer.domain.entity.sb;

import com.singer.domain.entity.BoardVo;
import java.util.List;

import com.singer.common.util.Constants.YES_NO;

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
public class SB01Vo extends BoardVo {

	private static final long serialVersionUID = -6895941086562069423L;

	private String video;

	private String videopath;

	private YES_NO videobool;

	private List<SB01Vo> list;

}
