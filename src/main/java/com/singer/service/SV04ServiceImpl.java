package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.AppException;
import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SV04Dao;
import com.singer.vo.SV04Vo;

@Service("sv04Service")
public class SV04ServiceImpl implements SV04Service {

	@Resource(name = "sv04Dao")
	private SV04Dao sv04Dao;

	@Override
	public int insertSV04Vo(SV04Vo sv04Vo) throws Exception {
		if (CommonUtil.isNull(sv04Vo.getText())) {
			throw new AppException("내용을 필수 입력해야 합니다");
		}
		sv04Vo.setRegdate(DateUtil.getTodayTime());

		return sv04Dao.insertSV04Vo(sv04Vo);
	}

	@Transactional
	@Override
	public int likeSV04Vo(SV04Vo sv04Vo) throws Exception {
		return sv04Dao.likeSV04Vo(sv04Vo);
	}

	@Override
	public List<SV04Vo> selectSV04Vo(SV04Vo sv04Vo, String userid) throws Exception {
		if (sv04Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sv04Vo.setTotCnt(sv04Dao.selectSV04Total(sv04Vo));
		}
		List<SV04Vo> list;
		if (sv04Vo.getParents() > 0) {
			list = sv04Dao.selectReplySV04Vo(sv04Vo);
		} else {
			list = sv04Dao.selectSV04Vo(sv04Vo);
		}

		Stream<SV04Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSV04Vo(SV04Vo sv04Vo) throws Exception {
		return sv04Dao.updateSV04Vo(sv04Vo);
	}

	@Transactional
	@Override
	public int deleteSV04Vo(SV04Vo sv04Vo) throws Exception {

		if (sv04Vo.getParents() > 0) {
			sv04Dao.deleteChild(sv04Vo);
			sv04Vo.setParents(0);
		}
		return sv04Dao.deleteSV04Vo(sv04Vo);
	}

}
