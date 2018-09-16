package com.singer.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SF02Vo;

@Repository("sf02Dao")
public class SF02Dao {
	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SF02";

	public int insertSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sb02Vo);
	}

	public int likeSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.update(namespace + ".like", sb02Vo);
	}

	public List<SF02Vo> selectSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sb02Vo);
	}

	public int updateSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.update(namespace + ".update", sb02Vo);
	}

	public int deleteSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sb02Vo);
	}

	public int delete_seqSF02Vo(SF02Vo sb02Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete_seq01", sb02Vo);
	}

}
