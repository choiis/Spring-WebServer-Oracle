package com.singer.application.service.comm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.util.Constants.USER_CODE;
import com.singer.common.util.DateUtil;
import com.singer.domain.dao.CommDao;
import com.singer.domain.entity.CommEntity;

@Service
public class CommServiceImpl implements CommService {

	@Autowired
	private CommDao commDao;

	@Override
	public List<CommEntity> selectCode(CommEntity vo) throws Exception {
		return commDao.selectCode(vo);
	}

	@Override
	public List<CommEntity> selectMenu(USER_CODE authlevel) throws Exception {

		Stream<CommEntity> stream = commDao.selectAllMenu().stream()
				.filter(s -> s.getAuthlevel().compareTo(authlevel) >= 0);
		List<CommEntity> list = new ArrayList<>();
		for (Iterator<CommEntity> i = stream.iterator(); i.hasNext();) {
			list.add(i.next());
		}
		return list;
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public List<CommEntity> insertMenu(CommEntity commVo, String userid, USER_CODE authlevel) throws Exception {
		commVo.setReguser(userid);
		commVo.setModuser(userid);
		commVo.setRegdate(DateUtil.getToday());
		commVo.setModdate(DateUtil.getToday());
		int cnt = commDao.insertMenu(commVo);

		commVo.setAuthlevel(authlevel);
		if (cnt != 0) {
			return commDao.selectMenu(commVo);
		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public List<CommEntity> updateMenu(CommEntity commVo, String userid, USER_CODE authlevel) throws Exception {
		commVo.setModuser(userid);
		commVo.setModdate(DateUtil.getToday());
		int cnt = commDao.updateMenu(commVo);

		commVo.setAuthlevel(authlevel);
		if (cnt != 0) {
			return commDao.selectMenu(commVo);
		} else {
			return null;
		}
	}

	@Transactional(rollbackFor = { Exception.class })
	@Override
	public List<CommEntity> deleteMenu(CommEntity commVo, USER_CODE authlevel) throws Exception {
		commDao.deleteMenu(commVo);
		commVo.setAuthlevel(authlevel);
		return commDao.selectMenu(commVo);
	}

	@Override
	public List<CommEntity> selectCodeGrp(CommEntity commVo) throws Exception {
		return commDao.selectCodeGrp(commVo);
	}

	@Override
	public List<CommEntity> insertCode(CommEntity commVo, String userid) throws Exception {

		commVo.setUsername(userid);
		commVo.setRegdate(DateUtil.getToday());
		commDao.insertCode(commVo);
		return commDao.selectCode(commVo);
	}

	@Override
	public List<CommEntity> deleteCode(CommEntity commVo) throws Exception {
		commDao.deleteCode(commVo);
		return commDao.selectCode(commVo);
	}

	@Override
	public int updateCode(CommEntity commVo) throws Exception {
		return commDao.updateCode(commVo);
	}

}
