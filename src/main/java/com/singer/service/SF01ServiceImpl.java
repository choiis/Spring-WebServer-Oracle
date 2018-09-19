package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SF01Dao;
import com.singer.dao.SF02Dao;
import com.singer.vo.SF01Vo;
import com.singer.vo.SF02Vo;

@Service("sf01Service")
public class SF01ServiceImpl implements SF01Service {
	@Resource(name = "sf01Dao")
	private SF01Dao sf01Dao;

	@Resource(name = "sf02Dao")
	private SF02Dao sf02Dao;

	@Override
	public int insertSF01Vo(SF01Vo sf01vo) throws Exception {

		sf01vo.setRegdate(DateUtil.getTodayTime());

		return sf01Dao.insertSF01Vo(sf01vo);
	}

	@Override
	public List<SF01Vo> selectSF01Vo(SF01Vo sf01vo) throws Exception {

		int nowPage = sf01vo.getNowPage();
		sf01vo.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE);
		sf01vo.setEndRownum(nowPage * Constants.ROW_PER_PAGE);

		return sf01Dao.selectSF01Vo(sf01vo);
	}

	@Override
	public List<SF01Vo> selectFindSF01Vo(SF01Vo sf01vo) throws Exception {
		int nowPage = sf01vo.getNowPage();
		sf01vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sf01vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

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
	public int likeSF01Vo(SF01Vo sf01Vo, String sessionid) throws Exception {
		int like = sf01Vo.getGood() + 1;
		sf01Dao.likeSF01Vo(sf01Vo);

		sf01Vo.setSessionid(sessionid);
		sf01Vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.likelogSF01Vo(sf01Vo);

		return like;
	}

	@Transactional
	@Override
	public int hateSF01Vo(SF01Vo sf01vo, String sessionid) throws Exception {
		int like = sf01vo.getGood() - 1;

		sf01Dao.hateSF01Vo(sf01vo);

		sf01vo.setSessionid(sessionid);
		sf01vo.setDatelog(DateUtil.getTodayTime());

		sf01Dao.hatelogSF01Vo(sf01vo);

		return like;
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
	public int insertFile(Map<String, Object> hashMap) throws Exception {
		return sf01Dao.insertFile(hashMap);
	}

	@Override
	public HashMap<String, Object> selectFile(SF01Vo SF01Vo) throws Exception {
		return sf01Dao.selectFile(SF01Vo);
	}

}
