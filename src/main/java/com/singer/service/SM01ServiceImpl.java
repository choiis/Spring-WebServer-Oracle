package com.singer.service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.exception.AppException;
import com.singer.exception.ClientException;
import com.singer.exception.ExceptionMsg;
import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.common.Constants.PHONE_INFO_CODE;
import com.singer.common.DateUtil;
import com.singer.dao.SM01Dao;
import com.singer.vo.SM01Vo;

import oracle.sql.BLOB;

@Service("sm01Service")
public class SM01ServiceImpl implements SM01Service {

	@Resource(name = "sm01Dao")
	private SM01Dao sm01Dao;

	// @Autowired
	// @Qualifier("aes256")
	// private AES256Util aes256Util;

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
		sm01Vo.setGrade(Constants.USER_CODE.NORMAL.getValue());

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
		sm01Vo.setInfocode(PHONE_INFO_CODE.CELL.getValue());
		sm01Dao.insertSMI1Vo(sm01Vo);
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
		sm01Vo = sm01Dao.selectOneSM01Vo(sm01Vo);
		List<SM01Vo> list = sm01Dao.selectSMI1Vo(sm01Vo);
		for (SM01Vo vo : list) {

			if (vo.getInfocode() == PHONE_INFO_CODE.CELL.getValue()) {
				sm01Vo.setCellpfnum(vo.getPfnum());
				sm01Vo.setCellpcnum(vo.getPcnum());
				sm01Vo.setCellpbnum(vo.getPbnum());
			} else if (vo.getInfocode() == PHONE_INFO_CODE.HOME.getValue()) {
				sm01Vo.setHomepfnum(vo.getPfnum());
				sm01Vo.setHomepcnum(vo.getPcnum());
				sm01Vo.setHomepbnum(vo.getPbnum());
			} else if (vo.getInfocode() == PHONE_INFO_CODE.COMPANY.getValue()) {
				sm01Vo.setCompanypfnum(vo.getPfnum());
				sm01Vo.setCompanypcnum(vo.getPcnum());
				sm01Vo.setCompanypbnum(vo.getPbnum());
			} else if (vo.getInfocode() == PHONE_INFO_CODE.OTHER.getValue()) {
				sm01Vo.setOtherpfnum(vo.getPfnum());
				sm01Vo.setOtherpcnum(vo.getPcnum());
				sm01Vo.setOtherpbnum(vo.getPbnum());
			}
		}
		return sm01Vo;
	}

	@Transactional
	@Override
	public SM01Vo login(SM01Vo sm01Vo) throws Exception {
		return sm01Dao.selectLoginSM01Vo(sm01Vo);
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
		if (CommonUtil.isNull(hashMap)) {
			throw new ClientException(HttpStatus.NOT_FOUND);
		} else { // 이미지 불러오기 성공시
			BLOB images = (BLOB) hashMap.get("PHOTO");

			is = images.getBinaryStream();

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
		// 전화번호 변경
		if (!CommonUtil.isNull(sm01Vo.getCellpcnum()) && !CommonUtil.isNull(sm01Vo.getCellpbnum())) {
			SM01Vo vo = new SM01Vo();
			vo.setUserid(sm01Vo.getUserid());
			vo.setInfocode(PHONE_INFO_CODE.CELL.getValue());
			vo.setPfnum(sm01Vo.getCellpfnum());
			vo.setPcnum(sm01Vo.getCellpcnum());
			vo.setPbnum(sm01Vo.getCellpbnum());
			vo.setRegdate(DateUtil.getToday());
			sm01Dao.insertSMI1Vo(vo);
		}

		if (!CommonUtil.isNull(sm01Vo.getHomepcnum()) && !CommonUtil.isNull(sm01Vo.getHomepbnum())) {
			SM01Vo vo = new SM01Vo();
			vo.setUserid(sm01Vo.getUserid());
			vo.setInfocode(PHONE_INFO_CODE.HOME.getValue());
			vo.setPfnum(sm01Vo.getHomepfnum());
			vo.setPcnum(sm01Vo.getHomepcnum());
			vo.setPbnum(sm01Vo.getHomepbnum());
			vo.setRegdate(DateUtil.getToday());
			sm01Dao.insertSMI1Vo(vo);
		}

		if (!CommonUtil.isNull(sm01Vo.getCompanypcnum()) && !CommonUtil.isNull(sm01Vo.getCompanypbnum())) {
			SM01Vo vo = new SM01Vo();
			vo.setUserid(sm01Vo.getUserid());
			vo.setInfocode(PHONE_INFO_CODE.COMPANY.getValue());
			vo.setPfnum(sm01Vo.getCompanypfnum());
			vo.setPcnum(sm01Vo.getCompanypcnum());
			vo.setPbnum(sm01Vo.getCompanypbnum());
			vo.setRegdate(DateUtil.getToday());
			sm01Dao.insertSMI1Vo(vo);
		}

		if (!CommonUtil.isNull(sm01Vo.getOtherpcnum()) && !CommonUtil.isNull(sm01Vo.getOtherpbnum())) {
			SM01Vo vo = new SM01Vo();
			vo.setUserid(sm01Vo.getUserid());
			vo.setInfocode(PHONE_INFO_CODE.OTHER.getValue());
			vo.setPfnum(sm01Vo.getOtherpfnum());
			vo.setPcnum(sm01Vo.getOtherpcnum());
			vo.setPbnum(sm01Vo.getOtherpbnum());
			vo.setRegdate(DateUtil.getToday());
			sm01Dao.insertSMI1Vo(vo);
		}

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

	@Transactional
	@Override
	public int updateSME1Vo(SM01Vo sm01Vo, String userId) throws Exception {
		sm01Vo.setInsertid(userId);
		sm01Dao.updateUserType(sm01Vo);
		if (sm01Vo.getUsertype() == Constants.USER_CODE.ADMIN.getValue()) {
			sm01Dao.insertSME1Vo(sm01Vo);
		} else {
			sm01Dao.deleteSME1Vo(sm01Vo);
		}
		return 0;
	}

}
