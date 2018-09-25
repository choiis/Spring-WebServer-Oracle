package com.singer.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.CommVo;

@Repository("commDao")
public class CommDao {
	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.Comm";

	public List<CommVo> selectCode(CommVo commVo) throws Exception {
		return sqlSession.selectList(namespace + ".selectCode", commVo);
	}

	public List<CommVo> selectMenu(CommVo commVo) throws Exception {
		return sqlSession.selectList(namespace + ".selectMenu", commVo);
	}

	public List<CommVo> selectCodeGrp(CommVo commVo) throws Exception {
		return sqlSession.selectList(namespace + ".selectCodeGrp", commVo);
	}

	public int insertMenu(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".insertMenu", commVo);
	}

	public int deleteMenu(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".deleteMenu", commVo);
	}

	public int updateMenu(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".updateMenu", commVo);
	}

	public int insertCode(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".insertCode", commVo);
	}

	public int deleteCode(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".deleteCode", commVo);
	}

	public int updateCode(CommVo commVo) throws Exception {
		return sqlSession.insert(namespace + ".updateCode", commVo);
	}
}
