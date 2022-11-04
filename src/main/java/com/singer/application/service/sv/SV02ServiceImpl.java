package com.singer.application.service.sv;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.singer.common.exception.AppException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.vo.sv.SV02Vo;

@Service
public class SV02ServiceImpl implements SV02Service {

	@Autowired
	private SV02Dao sv02Dao;

	@Override
	public int updateSV01Vo(SV02Vo sv02Vo) throws Exception {
		return sv02Dao.updateSV02Vo(sv02Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int insertSv03Vo(SV02Vo sv02Vo, String userid) throws Exception {
		List<SV02Vo> list = sv02Vo.getSv02Vos();
		if (CollectionUtils.isEmpty(list)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_8);
		}

		String regDate = DateUtil.getTodayTime();

		Stream<SV02Vo> stream = list.stream();
		stream.forEach(s -> {
			s.setUserid(userid);
			s.setRegdate(regDate);
		});
		sv02Dao.insertSV03Vo(list);
		return 1;
	}

}
