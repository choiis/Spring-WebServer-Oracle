package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.CommonUtil;
import com.singer.dao.SMI1Dao;
import com.singer.vo.SM01Vo;

@Service("smi1Service")
public class SMI1ServiceImpl implements SMI1Service {

	@Resource(name = "smi1Dao")
	private SMI1Dao smi1Dao;

	@Transactional
	@Override
	public List<SM01Vo> selectSMI1Vo(SM01Vo sm01Vo) throws Exception {

		List<SM01Vo> list = null;
		if (!CommonUtil.isNull(sm01Vo.getUsername())) {
			list = smi1Dao.selectByNameSMI1Vo(sm01Vo);
		} else if (!CommonUtil.isNull(sm01Vo.getBrth())) {
			list = smi1Dao.selectByBrthSMI1Vo(sm01Vo);
		} else if (!CommonUtil.isNull(sm01Vo.getCellpbnum())) {
			list = smi1Dao.selectByPhoneSMI1Vo(sm01Vo);
		}

		return list;
	}

}
