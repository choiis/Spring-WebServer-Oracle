package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SP01Dao;
import com.singer.dao.SP02Dao;
import com.singer.vo.SP01Vo;
import com.singer.vo.SP02Vo;

@Service("sp01Service")
public class SP01ServiceImpl implements SP01Service {
	@Resource(name = "sp01Dao")
	private SP01Dao sp01Dao;

	@Resource(name = "sp02Dao")
	private SP02Dao sp02Dao;

	@Override
	public int insertSP01Vo(SP01Vo sp01Vo) throws Exception {

		sp01Vo.setRegdate(DateUtil.getTodayTime());

		return sp01Dao.insertSP01Vo(sp01Vo);
	}

	@Override
	public List<SP01Vo> selectSP01Vo(SP01Vo sp01Vo) throws Exception {

		int nowPage = sp01Vo.getNowPage();
		sp01Vo.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE);
		sp01Vo.setEndRownum(nowPage * Constants.ROW_PER_PAGE);

		return sp01Dao.selectSP01Vo(sp01Vo);
	}

	@Override
	public List<SP01Vo> selectMyList(SP01Vo sp01Vo, String userid) throws Exception {
		int nowPage = sp01Vo.getNowPage();
		sp01Vo.setUserid(userid);
		sp01Vo.setStartRownum((nowPage - 1) * Constants.ROW_PER_PAGE);
		sp01Vo.setEndRownum(nowPage * Constants.ROW_PER_PAGE);

		return sp01Dao.selectMyListSP01Vo(sp01Vo);
	}

	@Override
	public List<SP01Vo> selectFindSP01Vo(SP01Vo sp01Vo) throws Exception {
		int nowPage = sp01Vo.getNowPage();
		sp01Vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sp01Vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

		if (sp01Vo.getSelection() == 1) { // 제목으로 검색
			sp01Vo.setTitle(sp01Vo.getFindText());
			sp01Vo.setUserid(null);
		} else { // 아이디로 검색
			sp01Vo.setUserid(sp01Vo.getFindText());
			sp01Vo.setTitle(null);
		}

		return sp01Dao.selectSP01Vo(sp01Vo);
	}

	@Transactional
	@Override
	public SP01Vo selectOneSP01Vo(SP01Vo sp01Vo, String userid) throws Exception {

		sp01Dao.clickSP01Vo(sp01Vo);
		sp01Vo.setSessionid(userid);
		SP01Vo SP01vo = sp01Dao.selectOneSP01Vo(sp01Vo);
		if (SP01vo.getUserid().equals(userid)) {
			SP01vo.setDeleteYn(true);
		}
		sp01Vo.setShowDate(DateUtil.getDateFormat(sp01Vo.getRegdate()));

		return SP01vo;
	}

	@Override
	public int updateSP01Vo(SP01Vo sp01Vo) throws Exception {
		return sp01Dao.updateSP01Vo(sp01Vo);
	}

	@Transactional
	@Override
	public int buySP01Vo(SP01Vo sp01Vo, String userid) throws Exception {

		sp01Vo.setRegisterid(userid);
		sp01Vo.setRegdate(DateUtil.getTodayTime());
		sp01Dao.insertSP03Vo(sp01Vo);
		return sp01Dao.buySP01Vo(sp01Vo);
	}

	@Override
	public int sellSP01Vo(SP01Vo sp01Vo) throws Exception {
		return sp01Dao.sellSP01Vo(sp01Vo);
	}

	@Override
	public int cancelSP01Vo(SP01Vo sp01Vo) throws Exception {
		return sp01Dao.cancelSP01Vo(sp01Vo);
	}

	@Transactional
	@Override
	public int likeSP01Vo(SP01Vo sp01Vo, String sessionid) throws Exception {
		int like = sp01Vo.getGood() + 1;
		sp01Dao.likeSP01Vo(sp01Vo);

		sp01Vo.setSessionid(sessionid);
		sp01Vo.setDatelog(DateUtil.getTodayTime());

		sp01Dao.likelogSP01Vo(sp01Vo);

		return like;
	}

	@Transactional
	@Override
	public int hateSP01Vo(SP01Vo sp01Vo, String sessionid) throws Exception {
		int like = sp01Vo.getGood() - 1;

		sp01Dao.hateSP01Vo(sp01Vo);

		sp01Vo.setSessionid(sessionid);
		sp01Vo.setDatelog(DateUtil.getTodayTime());

		sp01Dao.hatelogSP01Vo(sp01Vo);

		return like;
	}

	@Transactional
	@Override
	public int deleteSP01Vo(SP01Vo SP01Vo) throws Exception {

		SP02Vo sb02Vo = new SP02Vo();
		sb02Vo.setSeq01(SP01Vo.getSeq());

		sp02Dao.delete_seqSP02Vo(sb02Vo);

		return sp01Dao.deleteSP01Vo(SP01Vo);
	}

	@Override
	public int insertExplain(Map<String, Object> hashMap) throws Exception {
		return sp01Dao.insertExplain(hashMap);
	}

	@Override
	public HashMap<String, Object> selectExplain(SP01Vo sp01Vo) throws Exception {
		return sp01Dao.selectExplain(sp01Vo);
	}

}
