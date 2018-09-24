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

	public List<CommVo> selectCode(CommVo sb01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".selectCode", sb01Vo);
	}

	public List<CommVo> selectMenu(CommVo sb01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".selectMenu", sb01Vo);
	}
}
