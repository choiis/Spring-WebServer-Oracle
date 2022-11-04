package com.singer.service;

import java.io.File;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.util.PropertyUtil;
import com.singer.util.S3Util;
import com.singer.common.CommonUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.common.Constants.YES_NO;
import com.singer.common.DateUtil;
import com.singer.dao.SB01Dao;
import com.singer.dao.SB02Dao;
import com.singer.vo.SB01Vo;
import com.singer.vo.SB02Vo;

@Service
public class SB01ServiceImpl implements SB01Service {

	@Autowired
	private SB01Dao sb01Dao;

	@Autowired
	private SB02Dao sb02Dao;

	@Autowired
	S3Util s3Util;

	@Autowired
	private PropertyUtil propertyUtil;

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int insertSB01Vo(SB01Vo sb01Vo, MultipartHttpServletRequest request, String userid) throws Exception {

		sb01Vo.setUserid(userid);
		MultipartFile video = null;
		Iterator<String> itr = request.getFileNames();

		if (ObjectUtils.isEmpty(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		while (itr.hasNext()) {
			video = request.getFile(itr.next());
		}
		String timestamp = DateUtil.getTodayTime();
		StringBuilder sb = new StringBuilder("video/" + timestamp);
		sb01Vo.setRegdate(timestamp);

		if (CommonUtil.chkVideoFile(video.getOriginalFilename())) {
			sb01Vo.setVideobool(YES_NO.YES);
			sb.append(".mp4");
		} else if (CommonUtil.chkAudioFile(video.getOriginalFilename())) {
			sb01Vo.setVideobool(YES_NO.NO);
			sb.append(".mp3");
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_5);
		}

		String path = propertyUtil.getS3FilePath();
		File file = new File(path + "/" + sb.toString());
		video.transferTo(file);

		s3Util.putS3File(sb.toString(), file);

		sb01Dao.insertSB01Vo(sb01Vo);

		SB01Vo sb01Vo2 = new SB01Vo();
		sb01Vo2.setSeq(sb01Vo.getSeq());
		sb01Vo2.setRegdate(DateUtil.getToday());
		sb01Vo2.setVideopath(sb.toString());
		file.delete();
		return sb01Dao.insertVideo(sb01Vo2);

	}

	@Override
	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {

		return sb01Dao.selectSB01Vo(sb01Vo);
	}

	@Override
	public int selectSB01Count() throws Exception {
		SB01Vo vo = sb01Dao.selectSB01VoCount();
		return ObjectUtils.isEmpty(vo) ? 0 : CommonUtil.getPageCnt(vo.getTotCnt());
	}

	@Override
	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception {

		if (StringUtils.isEmpty(sb01Vo.getFindText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_10);
		} else if (sb01Vo.getSelection() == 1) { // 제목으로 검색
			sb01Vo.setTitle(sb01Vo.getFindText());
		} else if (sb01Vo.getSelection() == 2) { // 아이디로 검색
			sb01Vo.setUserid(sb01Vo.getFindText());
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_11);
		}

		return sb01Dao.selectFindSB01Vo(sb01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo, String userid) throws Exception {

		sb01Dao.clickSB01Vo(sb01Vo);
		sb01Vo.setSessionid(userid);
		SB01Vo sb01vo = sb01Dao.selectOneSB01Vo(sb01Vo);
		if (!ObjectUtils.isEmpty(sb01vo)) {
			if (userid.equals(sb01vo.getUserid())) {
				sb01vo.setDeleteYn(true);
			}
		}
		sb01Vo.setShowDate(DateUtil.getDateFormat(sb01Vo.getRegdate()));
		return sb01vo;
	}

	@Override
	public int updateSB01Vo(SB01Vo sb01Vo, MultipartHttpServletRequest request) throws Exception {

		MultipartFile video = null;
		Iterator<String> itr = request.getFileNames();

		if (!ObjectUtils.isEmpty(itr)) {
			while (itr.hasNext()) {
				video = request.getFile(itr.next());
			}
			String timestamp = DateUtil.getTodayTime();
			StringBuilder sb = new StringBuilder("video/" + timestamp);
			sb01Vo.setRegdate(timestamp);

			if (CommonUtil.chkVideoFile(video.getOriginalFilename())) {
				sb01Vo.setVideobool(YES_NO.YES);
				sb.append(".mp4");
			} else if (CommonUtil.chkAudioFile(video.getOriginalFilename())) {
				sb01Vo.setVideobool(YES_NO.NO);
				sb.append(".mp3");
			} else {
				throw new AppException(ExceptionMsg.EXT_MSG_INPUT_5);
			}

			String path = propertyUtil.getS3FilePath();
			File file = new File(path + "/" + sb.toString());
			video.transferTo(file);
			String deletedPath = sb01Dao.selectVideo(sb01Vo);
			s3Util.deleteS3File(deletedPath); // S3 파일삭제

			SB01Vo sb01Vo2 = new SB01Vo();
			sb01Vo2.setSeq(sb01Vo.getSeq());
			sb01Vo2.setRegdate(DateUtil.getToday());
			sb01Vo2.setVideopath(sb.toString());
			file.delete();

			sb01Dao.updateVideo(sb01Vo2);
		}

		return sb01Dao.updateSB01Vo(sb01Vo);
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SB01Vo likeSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception {
		sb01Dao.likeSB01Vo(sb01Vo);

		sb01Vo.setSessionid(sessionid);
		sb01Vo.setDatelog(DateUtil.getTodayTime());

		sb01Dao.likelogSB01Vo(sb01Vo);

		sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sb01Vo;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public SB01Vo hateSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception {
		sb01Dao.hateSB01Vo(sb01Vo);

		sb01Vo.setSessionid(sessionid);
		sb01Vo.setDatelog(DateUtil.getTodayTime());

		sb01Dao.hatelogSB01Vo(sb01Vo);

		sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sb01Vo;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception {

		SB02Vo sb02Vo = new SB02Vo();
		sb02Vo.setSeq01(sb01Vo.getSeq());

		sb02Dao.delete_seqSB02Vo(sb02Vo);

		return sb01Dao.deleteSB01Vo(sb01Vo);
	}

	@Override
	public InputStream selectVideo(SB01Vo sb01Vo, HttpServletRequest request) throws Exception {

		return s3Util.getS3FileStream(sb01Dao.selectVideo(sb01Vo));
	}

}
