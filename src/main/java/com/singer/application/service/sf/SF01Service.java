package com.singer.application.service.sf;

import com.singer.application.dto.sf.SF01Composer;
import com.singer.application.dto.sf.SF01ListResponse;
import com.singer.application.dto.sf.SF01Request;
import com.singer.application.dto.sf.SF01Response;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.util.CommonUtil;
import com.singer.common.util.DateUtil;
import com.singer.common.util.Constants.RESULT_CODE;
import com.singer.domain.dao.sf.SF01Dao;
import com.singer.domain.dao.sf.SF02Dao;
import com.singer.common.exception.AppException;
import com.singer.common.exception.ClientException;
import com.singer.common.exception.ExceptionMsg;
import com.singer.infrastructure.config.S3Properties;
import com.singer.infrastructure.util.S3Util;
import com.singer.domain.entity.sf.SF01Entity;
import com.singer.domain.entity.sf.SF02Entity;

import lombok.Cleanup;

@Service
public class SF01Service {

	@Autowired
	private SF01Dao sf01Dao;

	@Autowired
	private SF02Dao sf02Dao;

	@Autowired
	private S3Properties s3Properties;

	private static final String S3_FILE_PATH = "sf01/";

	@Autowired
	private S3Util s3Util;

	@Transactional(rollbackFor = { Exception.class })
	public SF01Response insertSF01(SF01Request sf01Request, MultipartHttpServletRequest request, String userid)
			throws Exception {

		SF01Entity sf01Entity = SF01Composer.requestToentity(sf01Request, userid);
		MultipartFile multipartFile = null;
		Iterator<String> itr = request.getFileNames();

		if (ObjectUtils.isEmpty(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		sf01Entity.setUserid(userid);
		sf01Entity.setRegdate(DateUtil.getTodayTime());

		while (itr.hasNext()) {
			multipartFile = request.getFile(itr.next());
		}
		String filename = multipartFile.getOriginalFilename();
		sf01Entity.setFilename(filename);

		String ftpfilename = sf01Entity.getRegdate() + "." + CommonUtil.getExtensionName(filename);

		sf01Entity.setFtpfilename(ftpfilename);
		File file = new File(s3Properties.getTempPath() + "/" + ftpfilename);
		multipartFile.transferTo(file);

		s3Util.putS3File(S3_FILE_PATH + ftpfilename, file);
		file.delete();

		int success = sf01Dao.insertSF01Vo(sf01Entity);
		sf01Entity.setResult(success);
		return SF01Composer.entityToResponse(sf01Entity);
	}

	public SF01ListResponse selectSF01List(int nowPage) throws Exception {

		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setNowPage(nowPage);
		List<SF01Entity> list = sf01Dao.selectSF01Vo(sf01Entity);
		SF01Entity entity = sf01Dao.selectSF01Count();
		int totalCount = ObjectUtils.isEmpty(entity) ? 0 : CommonUtil.getPageCnt(entity.getTotCnt());
		return SF01Composer.entityListToResponse(list, nowPage, totalCount);
	}

	public SF01Response selectOneSF01(int seq, String userid) throws Exception {

		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setSeq(seq);
		sf01Dao.clickSF01Vo(sf01Entity);
		sf01Entity.setSessionid(userid);
		sf01Entity = sf01Dao.selectOneSF01Vo(sf01Entity);
		if (!ObjectUtils.isEmpty(sf01Entity)) {
			if (userid.equals(sf01Entity.getUserid())) {
				sf01Entity.setDeleteYn(true);
			}
		}

		sf01Entity.setShowDate(DateUtil.getDateFormat(sf01Entity.getRegdate()));
		return SF01Composer.entityToResponse(sf01Entity);
	}

	public int updateSF01(SF01Entity entity) throws Exception {
		return sf01Dao.updateSF01Vo(entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SF01Response likeSF01(int seq, String sessionid) throws Exception {
		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setSeq(seq);
		sf01Dao.likeSF01Vo(sf01Entity);

		sf01Entity.setSessionid(sessionid);
		sf01Entity.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Entity);

		sf01Entity.setResult(RESULT_CODE.SUCCESS.getValue());
		return SF01Composer.entityToResponse(sf01Entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SF01Response hateSF01(int seq, String sessionid) throws Exception {
		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setSeq(seq);
		sf01Dao.hateSF01Vo(sf01Entity);

		sf01Entity.setSessionid(sessionid);
		sf01Entity.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01Entity);

		sf01Entity.setResult(RESULT_CODE.SUCCESS.getValue());
		return SF01Composer.entityToResponse(sf01Entity);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSF01(int seq, String sessionid) throws Exception {

		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setSeq(seq);
		sf01Entity.setSessionid(sessionid);
		SF01Entity sf01EntityResult = sf01Dao.selectOneSF01Vo(sf01Entity);

		if (!StringUtils.equals(sessionid, sf01EntityResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		SF02Entity sf02Entity = new SF02Entity();
		sf02Entity.setSeq01(sf01Entity.getSeq());

		sf02Dao.delete_seqSF02Vo(sf02Entity);

		sf01Entity = sf01Dao.selectFile(sf01Entity);

		s3Util.deleteS3File(S3_FILE_PATH + sf01Entity.getFtpfilename());

		return sf01Dao.deleteSF01Vo(sf01Entity);
	}

	public HashMap<String, Object> selectFile(int seq, String userid) throws Exception {

		SF01Entity sf01Entity = new SF01Entity();
		sf01Entity.setSeq(seq);
		sf01Entity = sf01Dao.selectFile(sf01Entity);

		String filename = sf01Entity.getFtpfilename();
		@Cleanup
		InputStream inputStream = s3Util.getS3FileStream(S3_FILE_PATH + filename);

		File file = new File(s3Properties.getTempPath() + "/" + filename);
		FileUtils.copyInputStreamToFile(inputStream, file);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("downfile", file);
		hashMap.put("filename", sf01Entity.getFilename());

		sf01Entity.setDownuserid(userid);
		sf01Entity.setRegdate(DateUtil.getTodayTime());
		sf01Dao.mergeSFD1Vo(sf01Entity);

		return hashMap;
	}

}
