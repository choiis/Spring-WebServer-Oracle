package com.singer.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.AES256Util;
import com.singer.exception.AppException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.common.Constants.YES_NO;
import com.singer.common.DateUtil;
import com.singer.dao.SM01Dao;
import com.singer.vo.SM01Vo;

import oracle.sql.BLOB;

@Service("sm01Service")
public class SM01ServiceImpl implements SM01Service {

	@Resource(name = "sm01Dao")
	private SM01Dao sm01Dao;

	@Autowired
	@Qualifier("aes256")
	private AES256Util aes256Util;

	@Transactional
	@Override
	public HashMap<String, Object> insertSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request) throws Exception {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (CommonUtil.isNull(sm01Vo.getUserid())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INF_1);
		}
		if (CommonUtil.isNull(sm01Vo.getPasswd())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INF_2);
		}

		if (CommonUtil.isNull(sm01Vo.getUsername())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INF_3);
		}

		// String pw = aes256Util.aesEncode(sm01Vo.getPasswd());
		// sm01Vo.setPasswd(pw);

		sm01Vo.setRegdate(DateUtil.getToday());
		sm01Vo.setGrade(Constants.USER_CODE_NORMAL);

		if (CommonUtil.isNull(sm01Vo.getAdminyn())) {
			sm01Vo.setAdminyn(YES_NO.NO.getValue());
		}

		MultipartFile photo = null;
		Iterator<String> itr = request.getFileNames();
		if (CommonUtil.isNull(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		while (itr.hasNext()) {
			photo = request.getFile(itr.next());
		}

		if (!CommonUtil.chkIMGFile(photo.getOriginalFilename())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
		}

		hashMap.put("succeed", sm01Dao.insertSM01Vo(sm01Vo));
		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", sm01Vo.getUserid());
		putHash.put("regdate", DateUtil.getToday());
		putHash.put("photo", photo.getBytes());

		sm01Dao.insertImage(putHash);

		return putHash;
	}

	@Transactional
	@Override
	public List<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception {

		int nowPage = sm01Vo.getNowPage();
		sm01Vo.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE);
		sm01Vo.setEndRownum(nowPage * Constants.ROW_PER_PAGE);

		return sm01Dao.selectSM01Vo(sm01Vo);
	}

	@Transactional
	@Override
	public SM01Vo selectOneSM01Vo(SM01Vo sm01Vo) throws Exception {

		return sm01Dao.selectOneSM01Vo(sm01Vo);
	}

	@Transactional
	@Override
	public int deleteSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sm01Dao.deleteSM01Vo(sm01Vo);
	}

	@Transactional
	@Override
	public InputStream selectImage(SM01Vo sm01Vo, HttpServletRequest request) throws Exception {

		InputStream is = null;

		HashMap<String, Object> hashMap = sm01Dao.selectImage(sm01Vo);
		try {
			if (CommonUtil.isNull(hashMap)) {
				String img_path = request.getSession().getServletContext().getRealPath("/resources/img/basic.jpg");
				File file = new File(img_path);
				is = new FileInputStream(file);

			} else { // 이미지 불러오기 성공시
				BLOB images = (BLOB) hashMap.get("PHOTO");

				is = images.getBinaryStream();

			}
		} catch (IOException e) {
		}

		return is;
	}

	@Transactional
	@Override
	public SM01Vo updateSM01Vo(SM01Vo sm01Vo, MultipartHttpServletRequest request, String userId) throws Exception {
		if (CommonUtil.isNull(sm01Vo.getUserid())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INF_1);
		}

		if (CommonUtil.isNull(sm01Vo.getUsername())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INF_3);
		}

		MultipartFile photo = null;
		Iterator<String> itr = request.getFileNames();

		while (itr.hasNext()) {
			photo = request.getFile(itr.next());
		}
		// 저장후
		sm01Dao.updateSM01Vo(sm01Vo);

		if (!CommonUtil.isNull(photo.getSize())) {

			if (!CommonUtil.chkIMGFile(photo.getOriginalFilename())) {
				throw new AppException(ExceptionMsg.EXT_MSG_INPUT_4);
			}
			HashMap<String, Object> putHash = new HashMap<String, Object>();
			putHash.put("userid", sm01Vo.getUserid());
			putHash.put("regdate", DateUtil.getToday());
			putHash.put("photo", photo.getBytes());

			sm01Dao.updateImage(putHash);
		}
		sm01Vo.setUserid(userId);
		// 재 조회
		return sm01Dao.selectOneSM01Vo(sm01Vo);
	}

}
