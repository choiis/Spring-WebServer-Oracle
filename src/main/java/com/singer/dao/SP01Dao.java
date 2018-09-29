package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.singer.vo.SP01Vo;

@Repository("sp01Dao")
public class SP01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SP01";

	public int insertSP01Vo(SP01Vo sp01vo) throws Exception {
		return insert(namespace + ".insert", sp01vo);
	}

	public int insertSP03Vo(SP01Vo sp01vo) throws Exception {
		return insert(namespace + ".insertSP03", sp01vo);
	}

	@SuppressWarnings("unchecked")
	public List<SP01Vo> selectSP01Vo(SP01Vo sp01vo) throws Exception {
		return (List<SP01Vo>) selectList(namespace + ".select", sp01vo);
	}

	@SuppressWarnings("unchecked")
	public List<SP01Vo> selectMyListSP01Vo(SP01Vo sp01vo) throws Exception {
		return (List<SP01Vo>) selectList(namespace + ".selectMyList", sp01vo);
	}

	public SP01Vo selectOneSP01Vo(SP01Vo sp01vo) throws Exception {
		return (SP01Vo) selectOne(namespace + ".selectOne", sp01vo);
	}

	public int updateSP01Vo(SP01Vo sp01vo) throws Exception {
		return update(namespace + ".update", sp01vo);
	}

	public int likeSP01Vo(SP01Vo sp01vo) throws Exception {
		return update(namespace + ".like", sp01vo);
	}

	public int hateSP01Vo(SP01Vo sp01vo) throws Exception {
		return update(namespace + ".hate", sp01vo);
	}

	public int clickSP01Vo(SP01Vo sp01vo) throws Exception {
		return update(namespace + ".click", sp01vo);
	}

	public int buySP01Vo(SP01Vo sp01Dao) throws Exception {
		return update(namespace + ".buyRegister", sp01Dao);
	}

	public int sellSP01Vo(SP01Vo sp01Dao) throws Exception {
		return update(namespace + ".sell", sp01Dao);
	}

	public int cancelSP01Vo(SP01Vo sp01Dao) throws Exception {
		return update(namespace + ".cancel", sp01Dao);
	}

	public int deleteSP01Vo(SP01Vo sp01Dao) throws Exception {
		return delete(namespace + ".delete", sp01Dao);
	}

	public int insertExplain(Map<String, Object> hashMap) throws Exception {
		return update(namespace + ".insertExplain", hashMap);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectExplain(SP01Vo sp01Dao) throws Exception {
		return (HashMap<String, Object>) selectOne(namespace + ".selectExplain", sp01Dao);
	}

	public int likelogSP01Vo(SP01Vo sp01Dao) throws Exception {
		return update(namespace + ".likelog", sp01Dao);
	}

	public int hatelogSP01Vo(SP01Vo sp01Dao) throws Exception {
		return update(namespace + ".hatelog", sp01Dao);
	}
}