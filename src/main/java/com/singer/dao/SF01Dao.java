package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SF01Vo;

@Repository("sf01Dao")
public class SF01Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SF01";

	public int insertSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", SF01Vo);
	}

	public List<SF01Vo> selectSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", SF01Vo);
	}

	public SF01Vo selectOneSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", SF01Vo);
	}

	public int updateSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".update", SF01Vo);
	}

	public int likeSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".like", SF01Vo);
	}

	public int hateSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".hate", SF01Vo);
	}

	public int clickSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".click", SF01Vo);
	}

	public int deleteSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", SF01Vo);
	}

	public int insertFile(Map<String, Object> hashMap) throws Exception {
		return sqlSession.update(namespace + ".insertFile", hashMap);
	}

	public HashMap<String, Object> selectFile(SF01Vo SF01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectFile", SF01Vo);
	}

	public int likelogSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".likelog", SF01Vo);
	}

	public int hatelogSF01Vo(SF01Vo SF01Vo) throws Exception {
		return sqlSession.update(namespace + ".hatelog", SF01Vo);
	}
}