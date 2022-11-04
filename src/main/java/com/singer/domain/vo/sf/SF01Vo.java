package com.singer.domain.vo.sf;

import com.singer.domain.vo.BoardVo;
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
public class SF01Vo extends BoardVo {

	private static final long serialVersionUID = 6040852554752588185L;

	private String filename;
	private String ftpfilename;
	private String downuserid;
	private int downcnt;

	private List<SF01Vo> list;

}
