package com.singer.application.service.comm;

import java.util.List;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.domain.entity.CommEntity;

public interface CommService {
	public List<CommEntity> selectCode(CommEntity vo) throws Exception;

	public List<CommEntity> selectMenu(USER_CODE authlevel) throws Exception;

	public List<CommEntity> insertMenu(CommEntity commVo, String userid, USER_CODE authlevel) throws Exception;

	public List<CommEntity> updateMenu(CommEntity commVo, String userid, USER_CODE authlevel) throws Exception;

	public List<CommEntity> deleteMenu(CommEntity commVo, USER_CODE authlevel) throws Exception;

	public List<CommEntity> selectCodeGrp(CommEntity commVo) throws Exception;

	public List<CommEntity> insertCode(CommEntity commVo, String userid) throws Exception;

	public List<CommEntity> deleteCode(CommEntity commVo) throws Exception;

	public int updateCode(CommEntity commVo) throws Exception;
}
