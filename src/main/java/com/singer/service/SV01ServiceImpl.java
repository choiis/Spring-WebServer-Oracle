package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.dao.SV02Dao;
import com.singer.dao.SV04Dao;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.common.Constants.YES_NO;
import com.singer.common.DateUtil;
import com.singer.dao.SV01Dao;
import com.singer.vo.SV01Vo;
import com.singer.vo.SV02Vo;
import com.singer.vo.SV04Vo;

@Service("sv01Service")
public class SV01ServiceImpl implements SV01Service {

	@Resource(name = "sv01Dao")
	private SV01Dao sv01Dao;

	@Resource(name = "sv02Dao")
	private SV02Dao sv02Dao;

	@Resource(name = "sv04Dao")
	private SV04Dao sv04Dao;

	@Transactional
	@Override
	public int insertSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {

		if (CommonUtil.isNull(sv01Vo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);
		}
		if (CommonUtil.isNull(sv01Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		List<SV02Vo> list = sv01Vo.getSv02Vos();

		String regDate = DateUtil.getTodayTime();
		sv01Vo.setUserid(userid);
		sv01Vo.setRegdate(regDate);
		
		if (CommonUtil.isZeroLength(list)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_8);
		} else {
			int size = list.size();
			for (int i = 0; i < size; i++) {
				if (CommonUtil.isNull(list.get(i).getContent())) {
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
		if (CommonUtil.isNull(sv01Vo.getFindText())) {
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

	@Transactional
	@Override
	public SV01Vo selectOneSV01Vo(SV01Vo sv01Vo, String userid) throws Exception {
		if (sv01Vo.getRecall() == YES_NO.NO.getValue()) {
			sv01Dao.clickSV01Vo(sv01Vo);
		}
		sv01Vo.setUserid(userid);
		sv01Vo = sv01Dao.selectOneSV01Vo(sv01Vo);
		if (!CommonUtil.isNull(sv01Vo)) {
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

	@Transactional
	@Override
	public int deleteSV01Vo(SV01Vo sv01Vo) throws Exception {

		SV04Vo sv04Vo = new SV04Vo();
		sv04Vo.setSeq(sv01Vo.getSeq());

		sv04Dao.delete_seqSV04Vo(sv04Vo);

		return sv01Dao.deleteSV01Vo(sv01Vo);
	}

	@Transactional
	@Override
	public SV01Vo likeSV01Vo(SV01Vo sv01Vo, String sessionid) throws Exception {
		sv01Dao.likeSV01Vo(sv01Vo);

		sv01Vo.setSessionid(sessionid);
		sv01Vo.setDatelog(DateUtil.getTodayTime());

		sv01Dao.likelogSV01Vo(sv01Vo);

		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sv01Vo;
	}

	@Transactional
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
