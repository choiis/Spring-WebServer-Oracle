package com.singer.service;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.CommonUtil;
import com.singer.common.DateUtil;
import com.singer.common.Constants.RESULT_CODE;
import com.singer.dao.SF01Dao;
import com.singer.dao.SF02Dao;
import com.singer.exception.AppException;
import com.singer.exception.ClientException;
import com.singer.exception.ExceptionMsg;
import com.singer.util.FTPUtil;
import com.singer.vo.SF01Vo;
import com.singer.vo.SF02Vo;

@Service("sf01Service")
public class SF01ServiceImpl implements SF01Service {
	@Resource(name = "sf01Dao")
	private SF01Dao sf01Dao;

	@Resource(name = "sf02Dao")
	private SF02Dao sf02Dao;

	@Inject
	FTPUtil ftp;

	@Transactional
	@Override
	public int insertSF01Vo(SF01Vo sf01Vo, MultipartHttpServletRequest request, String userid) throws Exception {

		if (CommonUtil.isNull(sf01Vo.getTitle())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_1);
		}
		if (CommonUtil.isNull(sf01Vo.getText())) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_2);
		}

		MultipartFile file = null;
		Iterator<String> itr = request.getFileNames();

		if (CommonUtil.isNull(itr)) {
			throw new AppException(ExceptionMsg.EXT_MSG_INPUT_3);
		}
		sf01Vo.setUserid(userid);
		sf01Vo.setRegdate(DateUtil.getTodayTime());

		while (itr.hasNext()) {
			file = request.getFile(itr.next());
		}
		String filename = file.getOriginalFilename();
		sf01Vo.setFilename(filename);

		String ftpfilename = sf01Vo.getRegdate() + "." + CommonUtil.getExtensionName(filename);

		sf01Vo.setFtpfilename(ftpfilename);
		int ok = sf01Dao.insertSF01Vo(sf01Vo);

		ftp.sendFile(ftpfilename, file);

		return ok;
	}

	@Override
	public List<SF01Vo> selectSF01Vo(SF01Vo sf01vo) throws Exception {

		return sf01Dao.selectSF01Vo(sf01vo);
	}

	@Override
	public List<SF01Vo> selectFindSF01Vo(SF01Vo sf01vo) throws Exception {

		if (CommonUtil.isNull(sf01vo.getFindText())) {
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

	@Transactional
	@Override
	public SF01Vo selectOneSF01Vo(SF01Vo sf01vo, String userid) throws Exception {

		sf01Dao.clickSF01Vo(sf01vo);
		sf01vo.setSessionid(userid);
		sf01vo = sf01Dao.selectOneSF01Vo(sf01vo);
		if (!CommonUtil.isNull(sf01vo)) {
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

	@Transactional
	@Override
	public SF01Vo likeSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		sf01Dao.likeSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sf01Vo;
	}

	@Transactional
	@Override
	public SF01Vo hateSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		sf01Dao.hateSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01Vo);

		sf01Vo.setResult(RESULT_CODE.SUCCESS.getValue());
		return sf01Vo;
	}

	@Transactional
	@Override
	public int deleteSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {

		sf01Vo.setSessionid(sessionid);
		SF01Vo checkVo = sf01Dao.checkUserSF01Vo(sf01Vo);
		if (!CommonUtil.isNull(checkVo)) {
			if (!checkVo.getUserid().equals(sessionid)) {
				throw new ClientException(HttpStatus.FORBIDDEN);
			}
		}

		SF02Vo sf02Vo = new SF02Vo();
		sf02Vo.setSeq01(sf01Vo.getSeq());

		sf02Dao.delete_seqSF02Vo(sf02Vo);

		sf01Vo = sf01Dao.selectFile(sf01Vo);

		ftp.deleteFile(sf01Vo.getFtpfilename());

		return sf01Dao.deleteSF01Vo(sf01Vo);
	}

	@Override
	public HashMap<String, Object> selectFile(SF01Vo sf01Vo) throws Exception {

		sf01Vo = sf01Dao.selectFile(sf01Vo);

		String filename = sf01Vo.getFtpfilename();
		File downloadFile = ftp.downFile(filename);

		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		hashMap.put("downfile", downloadFile);
		hashMap.put("filename", sf01Vo.getFilename());

		return hashMap;
	}

}
