package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SB01Vo;

@Repository("sb01Dao")
public class SB01Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SB01";

	public int insertSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sb01Vo);
	}

	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sb01Vo);
	}

	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", sb01Vo);
	}

	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".update", sb01Vo);
	}

	public int likeSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".like", sb01Vo);
	}

	public int hateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".hate", sb01Vo);
	}

	public int clickSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".click", sb01Vo);
	}

	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.delete(namespace + ".delete", sb01Vo);
	}

	public int insertVideo(Map<String, Object> hashMap) throws Exception {
		return sqlSession.update(namespace + ".insertVideo", hashMap);
	}

	public HashMap<String, Object> selectVideo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectVideo", sb01Vo);
	}

	public int likelogSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".likelog", sb01Vo);
	}

	public int hatelogSB01Vo(SB01Vo sb01Vo) throws Exception {
		return sqlSession.update(namespace + ".hatelog", sb01Vo);
	}
}