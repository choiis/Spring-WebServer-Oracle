package com.singer.service;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.dao.SF01Dao;
import com.singer.dao.SF02Dao;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.util.PropertyUtil;
import com.singer.util.S3Util;
import com.singer.vo.SF01Vo;
import com.singer.vo.SF02Vo;

import lombok.Cleanup;

@Service
public class SF01ServiceImpl implements SF01Service {

	@Autowired
	private SF01Dao sf01Dao;

	@Autowired
	private SF02Dao sf02Dao;

	@Autowired
	private PropertyUtil propertyUtil;

	private static final String S3_FILE_PATH = "sf01/";

	@Autowired
	private S3Util s3Util;

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int insertSF01Vo(SF01Vo sf01Vo, MultipartHttpServletRequest request, String userid) throws Exception {

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
		File file = new File(propertyUtil.getS3FilePath() + "/" + ftpfilename);
		multipartFile.transferTo(file);

		s3Util.putS3File(S3_FILE_PATH + ftpfilename, file);
		file.delete();

		return sf01Dao.insertSF01Vo(sf01Vo);
	}

	@Override
	public List<SF01Vo> selectSF01Vo(SF01Vo sf01vo) throws Exception {

		return sf01Dao.selectSF01Vo(sf01vo);
	}

	@Override
	public int selectSF01Count() throws Exception {
		SF01Vo vo = sf01Dao.selectSF01Count();
		return ObjectUtils.isEmpty(vo) ? 0 : CommonUtil.getPageCnt(vo.getTotCnt());
	}

	@Override
	public List<SF01Vo> selectFindSF01Vo(SF01Vo sf01vo) throws Exception {

		if (StringUtils.isEmpty(sf01vo.getFindText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_10);
		} else if (sf01vo.getSelection() == 1) { // 제목으로 검색
			sf01vo.setTitle(sf01vo.getFindText());
		} else if (sf01vo.getSelection() == 2) { // 아이디로 검색
			sf01vo.setUserid(sf01vo.getFindText());
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_11);
		}

		return sf01Dao.selectFindSF01Vo(sf01vo);
	}

	@Override
	public SF01Vo selectOneSF01Vo(SF01Vo sf01vo, String userid) throws Exception {

		sf01Dao.clickSF01Vo(sf01vo);
		sf01vo.setSessionid(userid);
		sf01vo = sf01Dao.selectOneSF01Vo(sf01vo);
		if (!ObjectUtils.isEmpty(sf01vo)) {
			if (userid.equals(sf01vo.getUserid())) {
				sf01vo.setDeleteYn(true);
			}
		}

		sf01vo.setShowDate(DateUtil.getDateFormat(sf01vo.getRegdate()));

		return sf01vo;
	}

	@Override
	public int updateSF01Vo(SF01Vo sf01vo) throws Exception {
		return sf01Dao.updateSF01Vo(sf01vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SF01Vo likeSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		sf01Dao.likeSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sf01Vo;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SF01Vo hateSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		sf01Dao.hateSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sf01Vo;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int deleteSF01Vo(SF01Vo sf01Vo) throws Exception {

		SF02Vo sf02Vo = new SF02Vo();
		sf02Vo.setSeq01(sf01Vo.getSeq());

		sf02Dao.delete_seqSF02Vo(sf02Vo);

		sf01Vo = sf01Dao.selectFile(sf01Vo);

		s3Util.deleteS3File(S3_FILE_PATH + sf01Vo.getFtpfilename());

		return sf01Dao.deleteSF01Vo(sf01Vo);
	}

	@Override
	public HashMap<String, Object> selectFile(SF01Vo sf01Vo, String userid) throws Exception {

		sf01Vo = sf01Dao.selectFile(sf01Vo);

		String filename = sf01Vo.getFtpfilename();
		@Cleanup
		InputStream inputStream = s3Util.getS3FileStream(S3_FILE_PATH + filename);

		File file = new File(propertyUtil.getS3FilePath() + "/" + filename);
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
