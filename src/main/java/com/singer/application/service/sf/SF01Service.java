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
	public SF01Response insertSF01Vo(SF01Request sf01Request, MultipartHttpServletRequest request, String userid)
			throws Exception {

		SF01Entity sf01Vo = SF01Composer.requestToentity(sf01Request, userid);
		MultipartFile multipartFile = null;
		Iterator<String> itr = request.getFileNames();

		if (ObjectUtils.isEmpty(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		sf01Vo.setUserid(userid);
		sf01Vo.setRegdate(DateUtil.getTodayTime());

		while (itr.hasNext()) {
			multipartFile = request.getFile(itr.next());
		}
		String filename = multipartFile.getOriginalFilename();
		sf01Vo.setFilename(filename);

		String ftpfilename = sf01Vo.getRegdate() + "." + CommonUtil.getExtensionName(filename);

		sf01Vo.setFtpfilename(ftpfilename);
		File file = new File(s3Properties.getTempPath() + "/" + ftpfilename);
		multipartFile.transferTo(file);

		s3Util.putS3File(S3_FILE_PATH + ftpfilename, file);
		file.delete();

		int success = sf01Dao.insertSF01Vo(sf01Vo);
		sf01Vo.setResult(success);
		return SF01Composer.entityToResponse(sf01Vo);
	}

	public SF01ListResponse selectSF01Vo(int nowPage) throws Exception {

		SF01Entity sf01Vo = new SF01Entity();
		sf01Vo.setNowPage(nowPage);
		List<SF01Entity> list = sf01Dao.selectSF01Vo(sf01Vo);
		SF01Entity vo = sf01Dao.selectSF01Count();
		int totalCount = ObjectUtils.isEmpty(vo) ? 0 : CommonUtil.getPageCnt(vo.getTotCnt());
		return SF01Composer.entityListToResponse(list, nowPage, totalCount);
	}

	public SF01Response selectOneSF01Vo(int seq, String userid) throws Exception {

		SF01Entity sf01vo = new SF01Entity();
		sf01vo.setSeq(seq);
		sf01Dao.clickSF01Vo(sf01vo);
		sf01vo.setSessionid(userid);
		sf01vo = sf01Dao.selectOneSF01Vo(sf01vo);
		if (!ObjectUtils.isEmpty(sf01vo)) {
			if (userid.equals(sf01vo.getUserid())) {
				sf01vo.setDeleteYn(true);
			}
		}

		sf01vo.setShowDate(DateUtil.getDateFormat(sf01vo.getRegdate()));
		return SF01Composer.entityToResponse(sf01vo);
	}

	public int updateSF01Vo(SF01Entity sf01vo) throws Exception {
		return sf01Dao.updateSF01Vo(sf01vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SF01Response likeSF01Vo(int seq, String sessionid) throws Exception {
		SF01Entity sf01Vo = new SF01Entity();
		sf01Vo.setSeq(seq);
		sf01Dao.likeSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return SF01Composer.entityToResponse(sf01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public SF01Response hateSF01Vo(int seq, String sessionid) throws Exception {
		SF01Entity sf01Vo = new SF01Entity();
		sf01Vo.setSeq(seq);
		sf01Dao.hateSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return SF01Composer.entityToResponse(sf01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	public int deleteSF01Vo(int seq, String sessionid) throws Exception {

		SF01Entity sf01Vo = new SF01Entity();
		sf01Vo.setSeq(seq);
		sf01Vo.setSessionid(sessionid);
		SF01Entity sf01voResult = sf01Dao.selectOneSF01Vo(sf01Vo);

		if (!StringUtils.equals(sessionid, sf01voResult.getUserid())) {
			throw new ClientException(HttpStatus.FORBIDDEN);
		}

		SF02Entity sf02Vo = new SF02Entity();
		sf02Vo.setSeq01(sf01Vo.getSeq());

		sf02Dao.delete_seqSF02Vo(sf02Vo);

		sf01Vo = sf01Dao.selectFile(sf01Vo);

		s3Util.deleteS3File(S3_FILE_PATH + sf01Vo.getFtpfilename());

		return sf01Dao.deleteSF01Vo(sf01Vo);
	}

	public HashMap<String, Object> selectFile(int seq, String userid) throws Exception {

		SF01Entity sf01Vo = new SF01Entity();
		sf01Vo.setSeq(seq);
		sf01Vo = sf01Dao.selectFile(sf01Vo);

		String filename = sf01Vo.getFtpfilename();
		@Cleanup
		InputStream inputStream = s3Util.getS3FileStream(S3_FILE_PATH + filename);

		File file = new File(s3Properties.getTempPath() + "/" + filename);
		FileUtils.copyInputStreamToFile(inputStream, file);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("downfile", file);
		hashMap.put("filename", sf01Vo.getFilename());

		sf01Vo.setDownuserid(userid);
		sf01Vo.setRegdate(DateUtil.getTodayTime());
		sf01Dao.mergeSFD1Vo(sf01Vo);

		return hashMap;
	}

}
