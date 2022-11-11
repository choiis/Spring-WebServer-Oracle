package com.singer.application.service.sv;

import com.singer.application.dto.sv.SV01Composer;
import com.singer.application.dto.sv.SV01ListResponse;
import com.singer.application.dto.sv.SV01Request;
import com.singer.application.dto.sv.SV01Response;
import com.singer.application.dto.sv.SV02Response;
import com.singer.common.util.CommonUtil;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import com.singer.domain.dao.sv.SV02Dao;
import com.singer.domain.dao.sv.SV04Dao;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.Constants.YES_NO;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sv.SV01Dao;
import com.singer.domain.entity.sv.SV01Vo;
import com.singer.domain.entity.sv.SV02Vo;
import com.singer.domain.entity.sv.SV04Vo;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SV01Service {

	@Autowired
	private SV01Dao sv01Dao;

	@Autowired
	private SV02Dao sv02Dao;

	@Autowired
	private SV04Dao sv04Dao;

	@Transactional(rollbackFor = { Exception.class })
	public SV01Response insertSV01Vo(SV01Request sv01Request, String userid) throws Exception {

		SV01Vo sv01Vo = SV01Composer.requestToEntity(sv01Request);
		List<SV02Vo> list = SV01Composer.requestListToEntityList(sv01Request.getList());

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

		sv01Dao.insertSV01Vo(sv01Vo);
		sv02Dao.insertSV02Vo(list);

		return SV01Composer.entityToResponse(sv01Vo, Collections.emptyList());
	}

	public SV01ListResponse selectSV01Vo(int nowPage) throws Exception {

		SV01Vo sv01Vo = new SV01Vo();
		sv01Vo.setNowPage(nowPage);
		List<SV01Vo> list = sv01Dao.selectSV01Vo(sv01Vo);
		int totalCount = ObjectUtils.isEmpty(list) ? 0 : CommonUtil.getPageCnt(list.get(0).getTotCnt());
		return SV01Composer.entityListToResponse(list, null, nowPage, totalCount);
	}

	public SV01Response selectOneSV01Vo(int seq, @PathVariable int recall, String userid) throws Exception {
		SV01Vo sv01Vo = new SV01Vo();
		sv01Vo.setSeq(seq);
		sv01Vo.setRecall(recall);
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
		sv01Vo.setSv02Vos(list);
		sv01Vo.setTotCnt(sv02Dao.selectCnt(sv02Vo));
		List<SV02Response> responseList = SV01Composer.entityListToResponseList(list);
		return SV01Composer.entityToResponse(sv01Vo, responseList);
	}

	public int updateSV01Vo(SV01Vo sv01Vo) throws Exception {
		return sv01Dao.updateSV01Vo(sv01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SV01Response deleteSV01Vo(int seq, String sessionid) throws Exception {

		SV01Vo sv01Vo = new SV01Vo();
		sv01Vo.setSeq(seq);
		sv01Vo.setSessionid(sessionid);
		sv01Vo.setUserid(sessionid);
		SV01Vo sv01voResult = sv01Dao.selectOneSV01Vo(sv01Vo);
		if (!StringUtils.equals(sessionid, sv01voResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		SV04Vo sv04Vo = new SV04Vo();
		sv04Vo.setSeq(sv01Vo.getSeq());

		sv04Dao.delete_seqSV04Vo(sv04Vo);

		sv01Dao.deleteSV01Vo(sv01Vo);
		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return SV01Composer.entityToResponse(sv01Vo, Collections.emptyList());
	}

	@Transactional(rollbackFor = { Exception.class })
	public SV01Response likeSV01Vo(int seq, String sessionid) throws Exception {
		SV01Vo sv01Vo = new SV01Vo();
		sv01Vo.setSeq(seq);
		sv01Dao.likeSV01Vo(sv01Vo);

		sv01Vo.setSessionid(sessionid);
		sv01Vo.setDatelog(DateUtil.getTodayTime());

		sv01Dao.likelogSV01Vo(sv01Vo);

		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return SV01Composer.entityToResponse(sv01Vo, Collections.emptyList());
	}

	@Transactional(rollbackFor = { Exception.class })
	public SV01Response hateSV01Vo(int seq, String sessionid) throws Exception {
		SV01Vo sv01Vo = new SV01Vo();
		sv01Vo.setSeq(seq);
		sv01Dao.hateSV01Vo(sv01Vo);

		sv01Vo.setSessionid(sessionid);
		sv01Vo.setDatelog(DateUtil.getTodayTime());

		sv01Dao.hatelogSV01Vo(sv01Vo);

		sv01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return SV01Composer.entityToResponse(sv01Vo, Collections.emptyList());
	}

}
