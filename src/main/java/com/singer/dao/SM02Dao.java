package com.singer.dao;

import java.util.List;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SM02Vo;

@Repository("sm02Dao")
public class SM02Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SM02";

	public int insertSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sm02Vo);
	}

	public List<SM02Vo> selectSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sm02Vo);
	}

	public SM02Vo selectOneSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", sm02Vo);
	}

	public int deleteSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sm02Vo);
	}

	public int updateSM02Vo(SM02Vo sm02Vo) throws Exception {
		return sqlSession.update(namespace + ".update", sm02Vo);
	}

}
