package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SB01Dao;
import com.singer.dao.SB02Dao;
import com.singer.vo.SB01Vo;
import com.singer.vo.SB02Vo;

@Service("sb01Service")
public class SB01ServiceImpl implements SB01Service {
	@Resource(name = "sb01Dao")
	private SB01Dao sb01Dao;

	@Resource(name = "sb02Dao")
	private SB02Dao sb02Dao;

	@Override
	public int insertSB01Vo(SB01Vo sb01Vo) throws Exception {

		sb01Vo.setRegdate(DateUtil.getTodayTime());

		return sb01Dao.insertSB01Vo(sb01Vo);
	}

	@Override
	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {

		int nowPage = sb01Vo.getNowPage();
		sb01Vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sb01Vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

		return sb01Dao.selectSB01Vo(sb01Vo);
	}

	@Override
	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception {
		int nowPage = sb01Vo.getNowPage();
		sb01Vo.setStartRownum(nowPage * Constants.ROW_PER_PAGE);
		sb01Vo.setEndRownum((nowPage + 1) * Constants.ROW_PER_PAGE);

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
		SB01Vo sb01vo = sb01Dao.selectOneSB01Vo(sb01Vo);
		if (sb01vo.getUserid().equals(userid)) {
			sb01vo.setDeleteYn(true);
		}

		return sb01vo;
	}

	@Override
	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sb01Dao.updateSB01Vo(sb01Vo);
	}

	@Override
	public int likeSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sb01Dao.likeSB01Vo(sb01Vo);
	}

	@Override
	public int hateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sb01Dao.hateSB01Vo(sb01Vo);
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
	public int insertVideo(Map<String, Object> hashMap) throws Exception {
		return sb01Dao.insertVideo(hashMap);
	}

	@Override
	public HashMap<String, Object> selectVideo(SB01Vo sb01Vo) throws Exception {
		return sb01Dao.selectVideo(sb01Vo);
	}

}
