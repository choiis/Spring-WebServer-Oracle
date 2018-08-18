package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SM01Vo;

@Repository("sm01Dao")
public class SM01DaoImpl implements SM01Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SM01";

	@Override
	public int insertSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sm01Vo);
	}

	@Override
	public int updateSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sqlSession.insert(namespace + ".update", sm01Vo);
	}

	@Override
	public List<SM01Vo> selectSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sm01Vo);
	}

	@Override
	public SM01Vo selectOneSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", sm01Vo);
	}

	@Override
	public int deleteSM01Vo(SM01Vo sm01Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sm01Vo);
	}

	@Override
	public int insertImage(Map<String, Object> hashMap) throws Exception {
		return sqlSession.update(namespace + ".insertImage", hashMap);
	}

	@Override
	public HashMap<String, Object> selectImage(SM01Vo sm01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectImage", sm01Vo);
	}

}
