package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.AppException;
import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.dao.SF02Dao;
import com.singer.vo.SF02Vo;

@Service("sf02Service")
public class SF02ServiceImpl implements SF02Service {

	@Resource(name = "sf02Dao")
	private SF02Dao sf02Dao;

	@Override
	public int insertSF02Vo(SF02Vo sf02Vo) throws Exception {
		if (CommonUtil.isNull(sf02Vo.getText())) {
			throw new AppException("내용을 필수 입력해야 합니다");
		}
		if (sf02Vo.getSeq01() < sf02Vo.getParents()) {
			throw new AppException("데이터 정합성 오류");
		}
		sf02Vo.setRegdate(DateUtil.getTodayTime());

		return sf02Dao.insertSF02Vo(sf02Vo);
	}

	@Override
	public int likeSF02Vo(SF02Vo sf02Vo) throws Exception {
		return sf02Dao.likeSF02Vo(sf02Vo);
	}

	@Override
	public List<SF02Vo> selectSF02Vo(SF02Vo sf02Vo, String userid) throws Exception {

		if (sf02Vo.getNowPage() == 1) { // 첫페이지 요청시 Total알아야한다
			sf02Vo.setTotCnt(sf02Dao.selectSF02Total(sf02Vo));
		}
		List<SF02Vo> list;
		if (sf02Vo.getParents() > 0) {
			list = sf02Dao.selectReplySF02Vo(sf02Vo);
		} else {
			list = sf02Dao.selectSF02Vo(sf02Vo);
		}

		for (int i = 0; i < list.size(); i++) {

			if (list.get(i).getUserid().equals(userid)) {
				list.get(i).setDeleteYn(true);
			}
		}

		return list;
	}

	@Override
	public int updateSF02Vo(SF02Vo sf02Vo) throws Exception {
		return sf02Dao.updateSF02Vo(sf02Vo);
	}

	@Transactional
	@Override
	public int deleteSF02Vo(SF02Vo sf02Vo) throws Exception {

		if (sf02Vo.getParents() > 0) {
			sf02Dao.deleteChild(sf02Vo);
			sf02Vo.setParents(0);
		}
		return sf02Dao.deleteSF02Vo(sf02Vo);
	}

}
