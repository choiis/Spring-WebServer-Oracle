package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.singer.domain.dao.sm.SMI1Dao;
import com.singer.common.exception.ClientException;
import com.singer.domain.entity.sm.SM01Vo;

@Service
public class SMI1ServiceImpl implements SMI1Service {

	@Autowired
	private SMI1Dao smi1Dao;

	@Override
	public List<SM01Vo> selectSMI1Vo(String searchCode, String searchParam) throws Exception {

		List<SM01Vo> list = null;
		SM01Vo sm01Vo = new SM01Vo();
		if (StringUtils.equals(searchCode, "username")) {
			sm01Vo.setUsername(searchParam);
			list = smi1Dao.selectByNameSMI1Vo(sm01Vo);
		} else if (StringUtils.equals(searchCode, "brth")) {
			sm01Vo.setBrth(searchParam);
			list = smi1Dao.selectByBrthSMI1Vo(sm01Vo);
		} else if (StringUtils.equals(searchCode, "cellpbnum")) {
			sm01Vo.setCellpbnum(searchParam);
			list = smi1Dao.selectByPhoneSMI1Vo(sm01Vo);
		} else {
			throw new ClientException(HttpStatus.BAD_REQUEST);
		}

		return list;
	}

}
