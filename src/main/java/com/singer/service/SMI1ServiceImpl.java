package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.singer.dao.SMI1Dao;
import com.singer.exception.ClientException;
import com.singer.vo.SM01Vo;

@Service("smi1Service")
public class SMI1ServiceImpl implements SMI1Service {

	@Resource(name = "smi1Dao")
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
