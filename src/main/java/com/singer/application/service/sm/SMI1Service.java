package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.singer.domain.dao.sm.SMI1Dao;
import com.singer.domain.entity.sm.SM01Entity;
import com.singer.application.dto.sm.SMI1Composer;
import com.singer.application.dto.sm.SMI1ListResponse;
import com.singer.common.exception.ClientException;

@Service
public class SMI1Service {

	@Autowired
	private SMI1Dao smi1Dao;

	public SMI1ListResponse selectSMI1List(String searchCode, String searchParam) throws Exception {

		List<SM01Entity> list = null;
		SM01Entity sm01Entity = new SM01Entity();
		if (StringUtils.equals(searchCode, "username")) {
			sm01Entity.setUsername(searchParam);
			list = smi1Dao.selectByNameSMI1(sm01Entity);
		} else if (StringUtils.equals(searchCode, "brth")) {
			sm01Entity.setBrth(searchParam);
			list = smi1Dao.selectByBrthSMI1(sm01Entity);
		} else if (StringUtils.equals(searchCode, "cellpbnum")) {
			sm01Entity.setCellpbnum(searchParam);
			list = smi1Dao.selectByPhoneSMI1(sm01Entity);
		} else {
			throw new ClientException(HttpStatus.BAD_REQUEST);
		}

		return SMI1Composer.entityListToResponse(list);
	}

}
