package com.singer.domain.entity.sf;

import com.singer.domain.entity.BoardEntity;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode(callSuper = true)
public class SF01Entity extends BoardEntity {

	private static final long serialVersionUID = 6040852554752588185L;

	private String filename;
	private String ftpfilename;
	private String downuserid;
	private int downcnt;

	private List<SF01Entity> list;

}
