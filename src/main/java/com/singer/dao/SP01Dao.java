package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import com.singer.vo.SP01Vo;

@Repository("sp01Dao")
public class SP01Dao {

	@Inject
	private SqlSession sqlSession;

	private static final String namespace = "com.singer.mappers.SP01";

	public int insertSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.insert(namespace + ".insert", sp01vo);
	}

	public int insertSP03Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.insert(namespace + ".insertSP03", sp01vo);
	}

	public List<SP01Vo> selectSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.selectList(namespace + ".select", sp01vo);
	}

	public List<SP01Vo> selectMyListSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.selectList(namespace + ".selectMyList", sp01vo);
	}

	public SP01Vo selectOneSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.selectOne(namespace + ".selectOne", sp01vo);
	}

	public int updateSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.update(namespace + ".update", sp01vo);
	}

	public int likeSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.update(namespace + ".like", sp01vo);
	}

	public int hateSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.update(namespace + ".hate", sp01vo);
	}

	public int clickSP01Vo(SP01Vo sp01vo) throws Exception {
		return sqlSession.update(namespace + ".click", sp01vo);
	}

	public int buySP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.update(namespace + ".buyRegister", sp01Dao);
	}

	public int sellSP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.update(namespace + ".sell", sp01Dao);
	}

	public int cancelSP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.update(namespace + ".cancel", sp01Dao);
	}

	public int deleteSP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.delete(namespace + ".delete", sp01Dao);
	}

	public int insertExplain(Map<String, Object> hashMap) throws Exception {
		return sqlSession.update(namespace + ".insertExplain", hashMap);
	}

	public HashMap<String, Object> selectExplain(SP01Vo sp01Dao) throws Exception {
		return sqlSession.selectOne(namespace + ".selectExplain", sp01Dao);
	}

	public int likelogSP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.update(namespace + ".likelog", sp01Dao);
	}

	public int hatelogSP01Vo(SP01Vo sp01Dao) throws Exception {
		return sqlSession.update(namespace + ".hatelog", sp01Dao);
	}
}