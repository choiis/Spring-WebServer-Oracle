package com.singer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.common.Constants.YES_NO;
import com.singer.common.DateUtil;
import com.singer.dao.SB01Dao;
import com.singer.dao.SB02Dao;
import com.singer.vo.SB01Vo;
import com.singer.vo.SB02Vo;

import oracle.sql.BLOB;

@Service("sb01Service")
public class SB01ServiceImpl implements SB01Service {
	@Resource(name = "sb01Dao")
	private SB01Dao sb01Dao;

	@Resource(name = "sb02Dao")
	private SB02Dao sb02Dao;

	@Transactional
	@Override
	public int insertSB01Vo(SB01Vo sb01Vo, MultipartHttpServletRequest request, String userid) throws Exception {

		if (CommonUtil.isNull(sb01Vo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);
		}
		if (CommonUtil.isNull(sb01Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		sb01Vo.setUserid(userid);
		MultipartFile video = null;
		Iterator<String> itr = request.getFileNames();

		if (CommonUtil.isNull(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		while (itr.hasNext()) {
			video = request.getFile(itr.next());
		}

		if (CommonUtil.chkVideoFile(video.getOriginalFilename())) {
			sb01Vo.setVideobool(YES_NO.YES.getValue());
		} else if (CommonUtil.chkAudioFile(video.getOriginalFilename())) {
			sb01Vo.setVideobool(YES_NO.NO.getValue());
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_5);
		}

		sb01Vo.setRegdate(DateUtil.getTodayTime());

		sb01Dao.insertSB01Vo(sb01Vo);
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("seq", sb01Vo.getSeq());
		putHash.put("regdate", DateUtil.getToday());
		putHash.put("video", video.getBytes());

		return sb01Dao.insertVideo(putHash);

	}

	@Override
	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {

		return sb01Dao.selectSB01Vo(sb01Vo);
	}

	@Override
	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception {

		if (sb01Vo.getSelection() == 1) { // 제목으로 검색
			sb01Vo.setTitle(sb01Vo.getFindText());
			sb01Vo.setUserid(null);
		} else { // 아이디로 검색
			sb01Vo.setUserid(sb01Vo.getFindText());
			sb01Vo.setTitle(null);
		}

		return sb01Dao.selectSB01Vo(sb01Vo);
	}

	@Transactional
	@Override
	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo, String userid) throws Exception {

		sb01Dao.clickSB01Vo(sb01Vo);
		sb01Vo.setSessionid(userid);
		SB01Vo sb01vo = sb01Dao.selectOneSB01Vo(sb01Vo);
		if (!CommonUtil.isNull(sb01vo)) {
			if (userid.equals(sb01vo.getUserid())) {
				sb01vo.setDeleteYn(true);
			}
		}
		sb01Vo.setShowDate(DateUtil.getDateFormat(sb01Vo.getRegdate()));

		return sb01vo;
	}

	@Override
	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception {
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("seq", sb01Vo.getSeq());
		putHash.put("regdate", DateUtil.getToday());
		putHash.put("video", sb01Vo.getVideo());

		sb01Dao.updateVideo(putHash);
		return sb01Dao.updateSB01Vo(sb01Vo);
	}

	@Transactional
	@Override
	public SB01Vo likeSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception {
		sb01Dao.likeSB01Vo(sb01Vo);

		sb01Vo.setSessionid(sessionid);
		sb01Vo.setDatelog(DateUtil.getTodayTime());

		sb01Dao.likelogSB01Vo(sb01Vo);

		sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sb01Vo;
	}

	@Transactional
	@Override
	public SB01Vo hateSB01Vo(SB01Vo sb01Vo, String sessionid) throws Exception {
		sb01Dao.hateSB01Vo(sb01Vo);

		sb01Vo.setSessionid(sessionid);
		sb01Vo.setDatelog(DateUtil.getTodayTime());

		sb01Dao.hatelogSB01Vo(sb01Vo);

		sb01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sb01Vo;
	}

	@Transactional
	@Override
	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception {

		SB02Vo sb02Vo = new SB02Vo();
		sb02Vo.setSeq01(sb01Vo.getSeq());

		sb02Dao.delete_seqSB02Vo(sb02Vo);

		return sb01Dao.deleteSB01Vo(sb01Vo);
	}

	@Override
	public InputStream selectVideo(SB01Vo sb01Vo, HttpServletRequest request) throws Exception {
		InputStream is = null;
		HashMap<String, Object> hashMap = sb01Dao.selectVideo(sb01Vo);

		if (CommonUtil.isNull(hashMap)) {
			String video_path = request.getSession().getServletContext().getRealPath("/resources/video/hyeri.mp4");
			File file = new File(video_path);
			is = new FileInputStream(file);
		} else {
			BLOB images = (BLOB) hashMap.get("VIDEO");

			is = images.getBinaryStream();
		}
		return is;
	}

}
