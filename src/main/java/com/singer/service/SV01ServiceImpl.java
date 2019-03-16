package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.dao.SV02Dao;
import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SV01Dao;
import com.singer.vo.SV01Vo;
import com.singer.vo.SV02Vo;

@Service("sv01Service")
public class SV01ServiceImpl implements SV01Service {

	@Resource(name = "sv01Dao")
	private SV01Dao sv01Dao;

	@Resource(name = "sv02Dao")
	private SV02Dao sv02Dao;

	@Transactional
	@Override
	public int insertSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {

		String regDate = DateUtil.getTodayTime();
		sv01Vo.setUserid(userid);
		sv01Vo.setRegdate(regDate);
		int result = sv01Dao.insertSV01Vo(sv01Vo);

		List<SV02Vo> list = sv01Vo.getSv02Vos();
		int leng = list.size();
		for (int i = 0; i < leng; i++) {
			list.get(i).setUserid(userid);
			list.get(i).setRegdate(regDate);
			sv02Dao.insertSV02Vo(list.get(i));
		}

		return result;
	}

	@Override
	public List<SV01Vo> selectSV01Vo(SV01Vo sv01Vo) throws Exception {
		sv01Vo.setRowPerPage(Constants.ROW_PER_PAGE);
		return sv01Dao.selectSV01Vo(sv01Vo);
	}

	@Transactional
	@Override
	public SV01Vo selectOneSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {
		sv01Dao.clickSV01Vo(sv01Vo);
		sv01Vo.setUserid(userid);
		sv01Vo = sv01Dao.selectOneSV01Vo(sv01Vo);

		SV02Vo sv02Vo = new SV02Vo();
		sv02Vo.setSeq(sv01Vo.getSeq());
		sv02Vo.setUserid(userid);

		List<SV02Vo> list = sv02Dao.selectSV02Vo(sv02Vo);
		sv01Vo.setSv02Vos(list); // 투표항목 정보
		sv01Vo.setTotCnt(sv02Dao.selectCnt(sv02Vo)); // 총 투표수

		return sv01Vo;
	}

	@Override
	public int updateSV01Vo(SV01Vo sv01Vo) throws Exception {
		return sv01Dao.updateSV01Vo(sv01Vo);
	}

	@Override
	public int deleteSV01Vo(SV01Vo sv01Vo) throws Exception {
		return sv01Dao.deleteSV01Vo(sv01Vo);
	}

}
