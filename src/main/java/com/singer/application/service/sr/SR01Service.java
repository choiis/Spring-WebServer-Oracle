package com.singer.application.service.sr;

import com.singer.application.dto.sr.SR01Composer;
import com.singer.application.dto.sr.SR01ListResponse;
import com.singer.application.dto.sr.SR01Request;
import com.singer.application.dto.sr.SR01Response;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.infrastructure.config.S3Properties;
import com.singer.infrastructure.util.S3Util;
import com.singer.common.util.CommonUtil;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.sr.SR01Dao;
import com.singer.domain.dao.sr.SR02Dao;
import com.singer.domain.dao.sr.SR03Dao;
import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;
import com.singer.domain.entity.sr.SR03Entity;

@Service
public class SR01Service {

	@Autowired
	private SR01Dao sr01Dao;

	@Autowired
	private SR02Dao sr02Dao;

	@Autowired
	private SR03Dao sr03Dao;

	@Autowired
	S3Util s3Util;

	@Autowired
	private S3Properties s3Properties;

	@Transactional(rollbackFor = { Exception.class })
	public SR01Response insertSR01(SR01Request sr01Request, MultipartHttpServletRequest request, String sessionid)
			throws Exception {

		SR01Entity sr01Entity = SR01Composer.requestToentity(sr01Request, sessionid);
		sr01Entity.setUserid(sessionid);
		sr01Entity.setRegdate(DateUtil.getTodayTime());

		sr01Dao.insertSR01(sr01Entity);
		SR02Entity sr02Entity = new SR02Entity(sr01Entity.getSeq(), sessionid, DateUtil.getTodayTime(), sr01Entity.getGrade());
		sr02Dao.insertSR02(sr02Entity);
		List<MultipartFile> fileList = request.getFiles("file");
		int idx = 0;
		ArrayList<SR01Entity> arrayList = new ArrayList<>();
		String today = DateUtil.getToday();
		String path = s3Properties.getTempPath();
		for (MultipartFile photo : fileList) {
			if (photo.getSize() == 0) {
				continue;
			}
			String originalFilename = photo.getOriginalFilename();
			if (!CommonUtil.chkIMGFile(originalFilename)) {
				throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
			}
			SR01Entity entity = new SR01Entity();
			entity.setSeq(sr01Entity.getSeq());
			entity.setIdx(idx++);
			entity.setRegdate(today);
			StringBuilder sb = new StringBuilder("rphoto/");
			sb.append(sr01Entity.getSeq() + "_" + today + "_" + idx + CommonUtil.getExtensionName(originalFilename));
			entity.setPhotopath(sb.toString());
			arrayList.add(entity);

			File file = new File(path + "/" + sb.toString());
			photo.transferTo(file);
			s3Util.putS3File(sb.toString(), file);
		}
		if (!CollectionUtils.isEmpty(arrayList)) {
			sr01Dao.insertImage(arrayList);
		}
		return SR01Composer.entityToResponse(sr01Entity);
	}

	public SR01ListResponse selectSR01List(int nowPage) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setNowPage(nowPage);
		List<SR01Entity> list = sr01Dao.selectSR01(sr01Entity);
		int totalCount = ObjectUtils.isEmpty(list) ? 0 : CommonUtil.getPageCnt(list.get(0).getTotCnt());
		return SR01Composer.entityListToResponse(list, nowPage, totalCount);
	}

	public SR01Response selectOneSR01(int seq, String userid) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setSeq(seq);
		sr01Dao.clickSR01(sr01Entity);

		sr01Entity.setUserid(userid);
		sr01Entity = sr01Dao.selectOneSR01(sr01Entity);
		if (!ObjectUtils.isEmpty(sr01Entity)) {
			if (userid.equals(sr01Entity.getUserid())) {
				sr01Entity.setDeleteYn(true);
			}
		}
		return SR01Composer.entityToResponse(sr01Entity);
	}

	public int updateSR01(SR01Entity entity) throws Exception {
		return sr01Dao.updateSR01(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SR01Response likeSR01(int seq, String sessionid) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setSeq(seq);
		sr01Dao.likeSR01(sr01Entity);

		sr01Entity.setSessionid(sessionid);
		sr01Entity.setDatelog(DateUtil.getTodayTime());

		sr01Dao.likelogSR01(sr01Entity);

		sr01Entity.setResult(RESULT_CODE.SUCCESS.getValue());
		return SR01Composer.entityToResponse(sr01Entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SR01Response hateSR01(int seq, String sessionid) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setSeq(seq);
		sr01Dao.hateSR01(sr01Entity);

		sr01Entity.setSessionid(sessionid);
		sr01Entity.setDatelog(DateUtil.getTodayTime());

		sr01Dao.hatelogSR01(sr01Entity);

		sr01Entity.setResult(RESULT_CODE.SUCCESS.getValue());
		return SR01Composer.entityToResponse(sr01Entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSR01(int seq, String sessionid) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setSeq(seq);
		sr01Entity.setSessionid(sessionid);
		sr01Entity.setUserid(sessionid);
		SR01Entity sr01EntityResult = sr01Dao.selectOneSR01(sr01Entity);

		if (!StringUtils.equals(sessionid, sr01EntityResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		SR03Entity sr03Entity = new SR03Entity();
		sr03Entity.setSeq(sr01Entity.getSeq());

		sr03Dao.deleteSeqSR03(sr03Entity);

		return sr01Dao.deleteSR01(sr01Entity);
	}

	public InputStream selectPhoto(int seq, int idx) throws Exception {
		SR01Entity sr01Entity = new SR01Entity();
		sr01Entity.setSeq(seq);
		sr01Entity.setIdx(idx);
		return s3Util.getS3FileStream(sr01Dao.selectPhoto(sr01Entity));
	}

}
