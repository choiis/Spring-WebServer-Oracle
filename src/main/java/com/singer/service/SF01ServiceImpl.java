package com.singer.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SF01Dao;
import com.singer.dao.SF02Dao;
import com.singer.vo.SF01Vo;
import com.singer.vo.SF02Vo;

import oracle.sql.BLOB;

@Service("sf01Service")
public class SF01ServiceImpl implements SF01Service {
	@Resource(name = "sf01Dao")
	private SF01Dao sf01Dao;

	@Resource(name = "sf02Dao")
	private SF02Dao sf02Dao;

	@Transactional
	@Override
	public int insertSF01Vo(SF01Vo sf01Vo, MultipartHttpServletRequest request, String userid) throws Exception {

		MultipartFile file = null;
		Iterator<String> itr = request.getFileNames();
		sf01Vo.setUserid(userid);
		sf01Vo.setRegdate(DateUtil.getTodayTime());
		String title = sf01Vo.getTitle();
		while (itr.hasNext()) {
			file = request.getFile(itr.next());
		}

		sf01Vo.setFilename(file.getOriginalFilename());

		sf01Dao.insertSF01Vo(sf01Vo);

		HashMap<String, Object> putHash = new HashMap<String, Object>();
		putHash.put("userid", userid);
		putHash.put("title", title);

		putHash.put("fileblob", file.getBytes());
		int ok = sf01Dao.insertFile(putHash);

		return ok;
	}

	@Override
	public List<SF01Vo> selectSF01Vo(SF01Vo sf01vo) throws Exception {

		return sf01Dao.selectSF01Vo(sf01vo);
	}

	@Override
	public List<SF01Vo> selectFindSF01Vo(SF01Vo sf01vo) throws Exception {

		if (sf01vo.getSelection() == 1) { // 제목으로 검색
			sf01vo.setTitle(sf01vo.getFindText());
			sf01vo.setUserid(null);
		} else { // 아이디로 검색
			sf01vo.setUserid(sf01vo.getFindText());
			sf01vo.setTitle(null);
		}

		return sf01Dao.selectSF01Vo(sf01vo);
	}

	@Transactional
	@Override
	public SF01Vo selectOneSF01Vo(SF01Vo sf01vo, String userid) throws Exception {

		sf01Dao.clickSF01Vo(sf01vo);
		sf01vo.setSessionid(userid);
		sf01vo = sf01Dao.selectOneSF01Vo(sf01vo);
		if (sf01vo.getUserid().equals(userid)) {
			sf01vo.setDeleteYn(true);
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
		int like = sf01Vo.getGood() + 1;
		sf01Dao.likeSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Vo);

		sf01Vo.setResult(Constants.SUCCESS_CODE);
		sf01Vo.setLike(like);
		return sf01Vo;
	}

	@Transactional
	@Override
	public SF01Vo hateSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		int like = sf01Vo.getGood() - 1;
		sf01Dao.hateSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01Vo);

		sf01Vo.setResult(Constants.SUCCESS_CODE);
		sf01Vo.setLike(like);
		return sf01Vo;
	}

	@Transactional
	@Override
	public int deleteSF01Vo(SF01Vo sf01vo) throws Exception {

		SF02Vo sf02Vo = new SF02Vo();
		sf02Vo.setSeq01(sf01vo.getSeq());

		sf02Dao.delete_seqSF02Vo(sf02Vo);

		return sf01Dao.deleteSF01Vo(sf01vo);
	}

	@Override
	public HashMap<String, Object> selectFile(SF01Vo SF01Vo) throws Exception {

		HashMap<String, Object> hashMap = sf01Dao.selectFile(SF01Vo);

		String filename = (String) hashMap.get("FILENAME");
		BLOB fileblob = (BLOB) hashMap.get("FILEBLOB");

		hashMap.put("fileblob", fileblob);
		hashMap.put("filename", filename);

		return hashMap;
	}

}
