package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.singer.vo.SR01Vo;

@Repository("sr01Dao")
public class SR01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR01";

	public int insertSR01Vo(SR01Vo sr01Vo) throws Exception {
		return insert(namespace + ".insert", sr01Vo);
	}

	public int deleteSR01Vo(SR01Vo sr01Vo) throws Exception {
		return delete(namespace + ".delete", sr01Vo);
	}

	public int updateSR01Vo(SR01Vo sr01Vo) throws Exception {
		return update(namespace + ".update", sr01Vo);
	}

	public int likeSR01Vo(SR01Vo sr01Vo) throws Exception {
		return update(namespace + ".like", sr01Vo);
	}

	public int hateSR01Vo(SR01Vo sr01Vo) throws Exception {
		return update(namespace + ".hate", sr01Vo);
	}

	public int clickSR01Vo(SR01Vo sr01Vo) throws Exception {
		return update(namespace + ".click", sr01Vo);
	}

	public int likelogSR01Vo(SR01Vo sr01Vo) throws Exception {
		return insert(namespace + ".likelog", sr01Vo);
	}

	public int hatelogSR01Vo(SR01Vo sr01Vo) throws Exception {
		return insert(namespace + ".hatelog", sr01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SR01Vo> selectSR01Vo(SR01Vo sr01Vo) throws Exception {
		return (List<SR01Vo>) selectList(namespace + ".select", sr01Vo);
	}

	public SR01Vo selectOneSR01Vo(SR01Vo sr01Vo) throws Exception {
		return (SR01Vo) selectOne(namespace + ".selectOne", sr01Vo);
	}

	public int insertImage(Map<String, Object> hashMap) throws Exception {
		return insert(namespace + ".insertPhoto", hashMap);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectPhoto(SR01Vo sr01Vo) throws Exception {
		return (HashMap<String, Object>) selectOne(namespace + ".selectPhoto", sr01Vo);
	}
}