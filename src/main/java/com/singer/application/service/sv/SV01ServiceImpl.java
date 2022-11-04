package com.singer.application.service.sv;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.dao.sv.SV04Dao;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.Constants.YES_NO;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sv.SV01Dao;
import com.singer.domain.vo.sv.SV01Vo;
import com.singer.domain.vo.sv.SV02Vo;
import com.singer.domain.vo.sv.SV04Vo;

@Service
public class SV01ServiceImpl implements SV01Service {

	@Autowired
	private SV01Dao sv01Dao;

	@Autowired
	private SV02Dao sv02Dao;

	@Autowired
	private SV04Dao sv04Dao;

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int insertSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {

		List<SV02Vo> list = sv01Vo.getSv02Vos();

		String regDate = DateUtil.getTodayTime();
		sv01Vo.setUserid(userid);
		sv01Vo.setRegdate(regDate);

		if (CollectionUtils.isEmpty(list)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_8);
		} else {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (StringUtils.isEmpty(list.get(i).getContent())) {
					throw new AppException(ExceptionMsg.EXT_MSG_INPUT_9);
				}
				list.get(i).setUserid(userid);
				list.get(i).setRegdate(regDate);
			}
		}

		int result = sv01Dao.insertSV01Vo(sv01Vo);
		sv02Dao.insertSV02Vo(list);

		return result;
	}

	@Override
	public List<SV01Vo> selectSV01Vo(SV01Vo sv01Vo) throws Exception {

		return sv01Dao.selectSV01Vo(sv01Vo);
	}

	@Override
	public List<SV01Vo> selectFindSV01Vo(SV01Vo sv01Vo) throws Exception {
		if (StringUtils.isEmpty(sv01Vo.getFindText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_10);
		} else if (sv01Vo.getSelection() == 1) { // �젣紐⑹쑝濡� 寃��깋
			sv01Vo.setTitle(sv01Vo.getFindText());
		} else if (sv01Vo.getSelection() == 2) { // �븘�씠�뵒濡� 寃��깋
			sv01Vo.setUserid(sv01Vo.getFindText());
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_11);
		}

		return sv01Dao.selectFindSV01Vo(sv01Vo);
	}

	@Override
	public SV01Vo selectOneSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {
		if (sv01Vo.getRecall() == YES_NO.NO.getValue()) {
			sv01Dao.clickSV01Vo(sv01Vo);
		}
		sv01Vo.setUserid(userid);
		sv01Vo = sv01Dao.selectOneSV01Vo(sv01Vo);
		if (!ObjectUtils.isEmpty(sv01Vo)) {
			if (userid.equals(sv01Vo.getUserid())) {
				sv01Vo.setDeleteYn(true);
			}
		}
		SV02Vo sv02Vo = new SV02Vo();
		sv02Vo.setSeq(sv01Vo.getSeq());
		sv02Vo.setUserid(userid);

		List<SV02Vo> list = sv02Dao.selectSV02Vo(sv02Vo);
		sv01Vo.setSv02Vos(list); // �닾�몴�빆紐� �젙蹂�
		sv01Vo.setTotCnt(sv02Dao.selectCnt(sv02Vo)); // 珥� �닾�몴�닔

		return sv01Vo;
	}

	@Override
	public int updateSV01Vo(SV01Vo sv01Vo) throws Exception {
		return sv01Dao.updateSV01Vo(sv01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int deleteSV01Vo(SV01Vo sv01Vo) throws Exception {

		SV04Vo sv04Vo = new SV04Vo();
		sv04Vo.setSeq(sv01Vo.getSeq());

		sv04Dao.delete_seqSV04Vo(sv04Vo);

		return sv01Dao.deleteSV01Vo(sv01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SV01Vo likeSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception {
		sv01Dao.likeSV01Vo(sv01Vo);

		sv01Vo.setSessionid(sessionid);
		sv01Vo.setDatelog(DateUtil.getTodayTime());

		sv01Dao.likelogSV01Vo(sv01Vo);

		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sv01Vo;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SV01Vo hateSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception {
		sv01Dao.hateSV01Vo(sv01Vo);

		sv01Vo.setSessionid(sessionid);
		sv01Vo.setDatelog(DateUtil.getTodayTime());

		sv01Dao.hatelogSV01Vo(sv01Vo);

		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sv01Vo;
	}

}
