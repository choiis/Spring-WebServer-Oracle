package com.singer.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.exception.AppException;
import com.singer.exception.ClientException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.common.DateUtil;
import com.singer.dao.SR01Dao;
import com.singer.dao.SR02Dao;
import com.singer.dao.SR03Dao;
import com.singer.vo.SR01Vo;
import com.singer.vo.SR03Vo;

import oracle.sql.BLOB;

@Service("sr01Service")
public class SR01ServiceImpl implements SR01Service {

	@Resource(name = "sr01Dao")
	private SR01Dao sr01Dao;

	@Resource(name = "sr02Dao")
	private SR02Dao sr02Dao;

	@Resource(name = "sr03Dao")
	private SR03Dao sr03Dao;

	@Transactional
	@Override
	public int insertSR01Vo(SR01Vo sr01Vo, MultipartHttpServletRequest request, String sessionid) throws Exception {
		if (CommonUtil.isNull(sr01Vo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);
		}
		if (CommonUtil.isNull(sr01Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}
		if (CommonUtil.isNull(sr01Vo.getMarkertitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_6);
		}
		if (sr01Vo.getGrade() < 0 || sr01Vo.getGrade() > 5) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_7);
		}

		sr01Vo.setUserid(sessionid);
		sr01Vo.setRegdate(DateUtil.getTodayTime());

		sr01Dao.insertSR01Vo(sr01Vo);
		sr02Dao.insertSR02Vo(sr01Vo);
		List<MultipartFile> fileList = request.getFiles("file");
		int idx = 0;
		ArrayList<HashMap<String, Object>> arrayList = new ArrayList<>();
		for (MultipartFile photo : fileList) {
			if (photo.getSize() == 0) {
				continue;
			}
			if (!CommonUtil.chkIMGFile(photo.getOriginalFilename())) {
				throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
			}
			HashMap<String, Object> putHash = new HashMap<String, Object>();
			putHash.put("seq", sr01Vo.getSeq());
			putHash.put("idx", idx++);
			putHash.put("regdate", DateUtil.getToday());
			putHash.put("photo", photo.getBytes());
			arrayList.add(putHash);
		}

		arrayList.stream().forEach(s -> {
			try {
				sr01Dao.insertImage(s);
			} catch (Exception e) {

			}
		});
		return sr01Vo.getSeq() > 0 ? 1 : 0;
	}

	@Override
	public List<SR01Vo> selectSR01Vo(SR01Vo sr01Vo) throws Exception {
		return sr01Dao.selectSR01Vo(sr01Vo);
	}

	@Override

	public List<SR01Vo> selectFindSR01Vo(SR01Vo sr01Vo) throws Exception {
		if (CommonUtil.isNull(sr01Vo.getFindText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_10);
		} else if (sr01Vo.getSelection() == 1) { // 제목으로 검색
			sr01Vo.setTitle(sr01Vo.getFindText());
		} else if (sr01Vo.getSelection() == 2) { // 아이디로 검색
			sr01Vo.setUserid(sr01Vo.getFindText());
		} else {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_11);
		}

		return sr01Dao.selectFindSR01Vo(sr01Vo);
	}

	@Transactional
	@Override
	public SR01Vo selectOneSR01Vo(SR01Vo sr01Vo, String userid) throws Exception {

		sr01Dao.clickSR01Vo(sr01Vo);

		sr01Vo.setUserid(userid);
		sr01Vo = sr01Dao.selectOneSR01Vo(sr01Vo);
		if (!CommonUtil.isNull(sr01Vo)) {
			if (userid.equals(sr01Vo.getUserid())) {
				sr01Vo.setDeleteYn(true);
			}
		}
		return sr01Vo;
	}

	@Override
	public int updateSR01Vo(SR01Vo sr01Vo) throws Exception {
		return sr01Dao.updateSR01Vo(sr01Vo);
	}

	@Transactional
	@Override
	public SR01Vo likeSR01Vo(SR01Vo sr01Vo, String sessionid) throws Exception {
		sr01Dao.likeSR01Vo(sr01Vo);

		sr01Vo.setSessionid(sessionid);
		sr01Vo.setDatelog(DateUtil.getTodayTime());

		sr01Dao.likelogSR01Vo(sr01Vo);

		sr01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sr01Vo;
	}

	@Transactional
	@Override
	public SR01Vo hateSR01Vo(SR01Vo sr01Vo, String sessionid) throws Exception {
		sr01Dao.hateSR01Vo(sr01Vo);

		sr01Vo.setSessionid(sessionid);
		sr01Vo.setDatelog(DateUtil.getTodayTime());

		sr01Dao.hatelogSR01Vo(sr01Vo);

		sr01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sr01Vo;
	}

	@Transactional
	@Override
	public int deleteSR01Vo(SR01Vo sr01Vo, String sessionid) throws Exception {
		sr01Vo.setSessionid(sessionid);
		SR01Vo checkVo = sr01Dao.selectOneSR01Vo(sr01Vo);
		if (!CommonUtil.isNull(checkVo)) {
			if (!checkVo.getUserid().equals(sessionid)) {
				throw new ClientException(HttpStatus.FORBIDDEN);
			}
		}
		SR03Vo sr03Vo = new SR03Vo();
		sr03Vo.setSeq(sr01Vo.getSeq());

		sr03Dao.delete_seqSR03Vo(sr03Vo);

		return sr01Dao.deleteSR01Vo(sr01Vo);
	}

	@Override
	public InputStream selectPhoto(SR01Vo sr01Vo) throws Exception {
		HashMap<String, Object> hashMap = sr01Dao.selectPhoto(sr01Vo);
		BLOB images = (BLOB) hashMap.get("PHOTO");

		return images.getBinaryStream();
	}

}
