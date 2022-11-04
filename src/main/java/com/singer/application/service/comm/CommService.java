package com.singer.application.service.comm;

import java.util.List;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.vo.CommVo;

public interface CommService {
	public List<CommVo> selectCode(CommVo vo) throws Exception;

	public List<CommVo> selectMenu(USER_CODE authlevel) throws Exception;

	public List<CommVo> insertMenu(CommVo commVo, String userid, USER_CODE authlevel) throws Exception;

	public List<CommVo> updateMenu(CommVo commVo, String userid, USER_CODE authlevel) throws Exception;

	public List<CommVo> deleteMenu(CommVo commVo, USER_CODE authlevel) throws Exception;

	public List<CommVo> selectCodeGrp(CommVo commVo) throws Exception;

	public List<CommVo> insertCode(CommVo commVo, String userid) throws Exception;

	public List<CommVo> deleteCode(CommVo commVo) throws Exception;

	public int updateCode(CommVo commVo) throws Exception;
}
