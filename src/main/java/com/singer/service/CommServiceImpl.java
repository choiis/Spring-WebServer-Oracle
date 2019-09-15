package com.singer.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.singer.common.DateUtil;
import com.singer.dao.CommDao;
import com.singer.vo.CommVo;

@Service("commService")
public class CommServiceImpl implements CommService {

	@Resource(name = "commDao")
	private CommDao commDao;

	@Override
	public List<CommVo> selectCode(CommVo vo) throws Exception {
		return commDao.selectCode(vo);
	}

	@Override
	public List<CommVo> selectMenu(String authlevel) throws Exception {
		CommVo commVo = new CommVo();
		commVo.setAuthlevel(authlevel);
		return commDao.selectMenu(commVo);
	}

	@Transactional
	@Override
	public List<CommVo> insertMenu(CommVo commVo, String userid, String authlevel) throws Exception {
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

	@Transactional
	@Override
	public List<CommVo> updateMenu(CommVo commVo, String userid, String authlevel) throws Exception {
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

	@Transactional
	@Override
	public List<CommVo> deleteMenu(CommVo commVo, String authlevel) throws Exception {
		commDao.deleteMenu(commVo);
		commVo.setAuthlevel(authlevel);
		return commDao.selectMenu(commVo);
	}

	@Override
	public List<CommVo> selectCodeGrp(CommVo commVo) throws Exception {
		return commDao.selectCodeGrp(commVo);
	}

	@Override
	public List<CommVo> insertCode(CommVo commVo, String userid) throws Exception {

		commVo.setUsername(userid);
		commVo.setRegdate(DateUtil.getToday());
		commDao.insertCode(commVo);
		return commDao.selectCode(commVo);
	}

	@Override
	public List<CommVo> deleteCode(CommVo commVo) throws Exception {
		commDao.deleteCode(commVo);
		return commDao.selectCode(commVo);
	}

	@Override
	public int updateCode(CommVo commVo) throws Exception {
		return commDao.updateCode(commVo);
	}

}
