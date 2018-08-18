package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SB01Vo;

@Repository("sb01Dao")
public class SB01DaoImpl implements SB01Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SB01";

	@Override
	public int insertSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sb01Vo);
	}

	@Override
	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sb01Vo);
	}

	@Override
	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", sb01Vo);
	}

	@Override
	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".update", sb01Vo);
	}

	@Override
	public int likeSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".like", sb01Vo);
	}

	@Override
	public int hateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".hate", sb01Vo);
	}

	@Override
	public int clickSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".click", sb01Vo);
	}

	@Override
	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sb01Vo);
	}

	@Override
	public int insertVideo(Map<String, Object> hashMap) throws Exception {
		return sqlSession.update(namespace + ".insertVideo", hashMap);
	}

	@Override
	public HashMap<String, Object> selectVideo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectVideo", sb01Vo);
	}
}