package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.singer.domain.dao.sm.SMI1Dao;
import com.singer.domain.entity.sm.SM01Entity;
import com.singer.common.exception.ClientException;

@Service
public class SMI1Service {

	@Autowired
	private SMI1Dao smi1Dao;


	public List<SM01Entity> selectSMI1Vo(String searchCode, String searchParam) throws Exception {

		List<SM01Entity> list = null;
		SM01Entity sm01Entity = new SM01Entity();
		if (StringUtils.equals(searchCode, "username")) {
			sm01Entity.setUsername(searchParam);
			list = smi1Dao.selectByNameSMI1Vo(sm01Entity);
		} else if (StringUtils.equals(searchCode, "brth")) {
			sm01Entity.setBrth(searchParam);
			list = smi1Dao.selectByBrthSMI1Vo(sm01Entity);
		} else if (StringUtils.equals(searchCode, "cellpbnum")) {
			sm01Entity.setCellpbnum(searchParam);
			list = smi1Dao.selectByPhoneSMI1Vo(sm01Entity);
		} else {
			throw new ClientException(HttpStatus.BAD_REQUEST);
		}

		return list;
	}

}
