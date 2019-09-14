package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.AppException;
import com.singer.common.DateUtil;
import com.singer.dao.SV02Dao;
import com.singer.vo.SV02Vo;

@Service("sv02Service")
public class SV02ServiceImpl implements SV02Service {

	@Resource(name = "sv02Dao")
	private SV02Dao sv02Dao;

	@Override
	public int updateSV01Vo(SV02Vo sv02Vo) throws Exception {
		return sv02Dao.updateSV02Vo(sv02Vo);
	}

	@Transactional
	@Override
	public int insertSv03Vo(SV02Vo sv02Vo, String userid) throws Exception {
		List<SV02Vo> list = sv02Vo.getSv02Vos();
		if (list.size() == 0) {
			throw new AppException("투표항목을 필수 입력해야 합니다");
		}

		String regDate = DateUtil.getTodayTime();

		Stream<SV02Vo> stream = list.stream();
		stream.forEach(s -> {
			s.setUserid(userid);
			s.setRegdate(regDate);
			try {
				sv02Dao.insertSV03Vo(s);
			} catch (Exception e) {
			}
		});

		return 1;
	}

}
