package com.singer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.CommonUtil;
import com.singer.common.Constants;
import com.singer.common.DateUtil;
import com.singer.dao.SM01Dao;
import com.singer.vo.SM01Vo;

@Service("sm01Service")
public class SM01ServiceImpl implements SM01Service {

	@Resource(name = "sm01Dao")
	private SM01Dao sm01Dao;

	@Transactional
	@Override
	public HashMap<String, Object> insertSM01Vo(SM01Vo sm01Vo) throws Exception {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();

		if (CommonUtil.isNull(sm01Vo.getAdminyn())) {
			sm01Vo.setAdminyn(Constants.YES_N);
		}

		sm01Vo.setRegdate(DateUtil.getToday());

		hashMap.put("succeed", sm01Dao.insertSM01Vo(sm01Vo));

		return hashMap;
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

	@Override
	public int insertImage(Map<String, Object> hashMap) throws Exception {
		return sm01Dao.insertImage(hashMap);
	}

	@Override
	public HashMap<String, Object> selectImage(SM01Vo sm01Vo) throws Exception {
		return sm01Dao.selectImage(sm01Vo);
	}

	@Transactional
	@Override
	public SM01Vo updateSM01Vo(SM01Vo sm01Vo) throws Exception {
		// 저장후
		sm01Dao.updateSM01Vo(sm01Vo);
		// 재 조회
		return sm01Dao.selectOneSM01Vo(sm01Vo);
	}

}
