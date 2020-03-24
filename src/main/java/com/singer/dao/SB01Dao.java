package com.singer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.singer.vo.SB01Vo;

@Repository("sb01Dao")
public class SB01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SB01";

	public int insertSB01Vo(SB01Vo sb01Vo) throws Exception {
		return insert(namespace + ".insert", sb01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SB01Vo> selectSB01Vo(SB01Vo sb01Vo) throws Exception {
		return (List<SB01Vo>) selectList(namespace + ".select", sb01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SB01Vo> selectFindSB01Vo(SB01Vo sb01Vo) throws Exception {
		return (List<SB01Vo>) selectList(namespace + ".selectFind", sb01Vo);
	}

	public SB01Vo selectOneSB01Vo(SB01Vo sb01Vo) throws Exception {
		return (SB01Vo) selectOne(namespace + ".selectOne", sb01Vo);
	}

	public SB01Vo checkUserSB01Vo(SB01Vo sb01Vo) throws Exception {
		return (SB01Vo) selectOne(namespace + ".checkUser", sb01Vo);
	}

	public int updateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return update(namespace + ".update", sb01Vo);
	}

	public int likeSB01Vo(SB01Vo sb01Vo) throws Exception {
		return insert(namespace + ".like", sb01Vo);
	}

	public int hateSB01Vo(SB01Vo sb01Vo) throws Exception {
		return insert(namespace + ".hate", sb01Vo);
	}

	public int clickSB01Vo(SB01Vo sb01Vo) throws Exception {
		return update(namespace + ".click", sb01Vo);
	}

	public int deleteSB01Vo(SB01Vo sb01Vo) throws Exception {
		return delete(namespace + ".delete", sb01Vo);
	}

	public int insertVideo(Map<String, Object> hashMap) throws Exception {
		return insert(namespace + ".insertVideo", hashMap);
	}

	public int updateVideo(Map<String, Object> hashMap) throws Exception {
		return insert(namespace + ".updateVideo", hashMap);
	}

	@SuppressWarnings("unchecked")
	public HashMap<String, Object> selectVideo(SB01Vo sb01Vo) throws Exception {
		return (HashMap<String, Object>) selectOne(namespace + ".selectVideo", sb01Vo);
	}

	public int likelogSB01Vo(SB01Vo sb01Vo) throws Exception {
		return update(namespace + ".likelog", sb01Vo);
	}

	public int hatelogSB01Vo(SB01Vo sb01Vo) throws Exception {
		return update(namespace + ".hatelog", sb01Vo);
	}
}