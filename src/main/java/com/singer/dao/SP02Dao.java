package com.singer.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SP02Vo;

@Repository("sp02Dao")
public class SP02Dao {
	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SP02";

	public int insertSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sp02Vo);
	}

	public int likeSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.update(namespace + ".like", sp02Vo);
	}

	public List<SP02Vo> selectSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sp02Vo);
	}

	public int updateSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.update(namespace + ".update", sp02Vo);
	}

	public int deleteSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sp02Vo);
	}

	public int delete_seqSP02Vo(SP02Vo sp02Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete_seq01", sp02Vo);
	}

}
