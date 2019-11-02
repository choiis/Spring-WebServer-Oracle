package com.singer.service;

import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SB02Dao;
import com.singer.vo.SB02Vo;

@Service("sb02Service")
public class SB02ServiceImpl implements SB02Service {

	@Resource(name = "sb02Dao")
	private SB02Dao sb02Dao;

	@Override
	public int insertSB02Vo(SB02Vo sb02Vo, String userid) throws Exception {
		if (CommonUtil.isNull(sb02Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}
		sb02Vo.setUserid(userid);
		sb02Vo.setRegdate(DateUtil.getTodayTime());

		return sb02Dao.insertSB02Vo(sb02Vo);
	}

	@Override
	public int likeSB02Vo(SB02Vo sb02Vo) throws Exception {
		return sb02Dao.likeSB02Vo(sb02Vo);
	}

	@Transactional
	@Override
	public List<SB02Vo> selectSB02Vo(SB02Vo sb02Vo, String userid) throws Exception {
		if (sb02Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sb02Vo.setTotCnt(sb02Dao.selectSF02Total(sb02Vo));
		}
		List<SB02Vo> list;
		if (sb02Vo.getParents() > 0) {
			list = sb02Dao.selectReplySB02Vo(sb02Vo);
		} else {
			list = sb02Dao.selectSB02Vo(sb02Vo);
		}

		Stream<SB02Vo> stream = list.stream();
		stream.forEach(s -> {
			if (userid.equals(s.getUserid())) {
				s.setDeleteYn(true);
			}
		});

		return list;
	}

	@Override
	public int updateSB02Vo(SB02Vo sb02Vo) throws Exception {
		return sb02Dao.updateSB02Vo(sb02Vo);
	}

	@Transactional
	@Override
	public int deleteSB02Vo(SB02Vo sb02Vo) throws Exception {
		if (sb02Vo.getParents() > 0) {
			sb02Dao.deleteChild(sb02Vo);
			sb02Vo.setParents(0);
		}
		return sb02Dao.deleteSB02Vo(sb02Vo);
	}

}
