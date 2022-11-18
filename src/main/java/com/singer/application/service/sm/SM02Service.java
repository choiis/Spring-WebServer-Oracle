package com.singer.application.service.sm;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.application.dto.sm.SM02Composer;
import com.singer.application.dto.sm.SM02ListResponse;
import com.singer.application.dto.sm.SM02Request;
import com.singer.application.dto.sm.SM02Response;
import com.singer.domain.dao.sm.SM02Dao;
import com.singer.domain.entity.sm.SM02Entity;

@Service
public class SM02Service {

	@Autowired
	private SM02Dao sm02Dao;

	@Transactional(rollbackFor = { Exception.class })
	public SM02Response insertSM02(SM02Request request, String userid) throws Exception {

		SM02Entity entity = SM02Composer.requestToEntity(request, userid);
		sm02Dao.insertSM02(entity);
		return SM02Composer.entityToResponse(entity);
	}

	public SM02ListResponse selectSM02List(int nowPage, String userid) throws Exception {

		SM02Entity entity = new SM02Entity();
		entity.setUserid(userid);
		entity.setNowPage(nowPage);
		List<SM02Entity> list = sm02Dao.selectSM02(entity);
		return SM02Composer.entityListToResponse(list);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSM02(int seq, String userid) throws Exception {
		SM02Entity entity = new SM02Entity();
		entity.setSeq(seq);
		entity.setUserid(userid);
		return sm02Dao.deleteSM02(entity);
	}

}
