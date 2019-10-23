package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SF01Vo;

@Repository("sf01Dao")
public class SF01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SF01";

	public int insertSF01Vo(SF01Vo SF01Vo) throws Exception {
		return insert(namespace + ".insert", SF01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SF01Vo> selectSF01Vo(SF01Vo SF01Vo) throws Exception {
		return (List<SF01Vo>) selectList(namespace + ".select", SF01Vo);
	}

	public SF01Vo selectOneSF01Vo(SF01Vo SF01Vo) throws Exception {
		return (SF01Vo) selectOne(namespace + ".selectOne", SF01Vo);
	}

	public int updateSF01Vo(SF01Vo SF01Vo) throws Exception {
		return update(namespace + ".update", SF01Vo);
	}

	public int likeSF01Vo(SF01Vo SF01Vo) throws Exception {
		return insert(namespace + ".like", SF01Vo);
	}

	public int hateSF01Vo(SF01Vo SF01Vo) throws Exception {
		return insert(namespace + ".hate", SF01Vo);
	}

	public int clickSF01Vo(SF01Vo SF01Vo) throws Exception {
		return update(namespace + ".click", SF01Vo);
	}

	public int deleteSF01Vo(SF01Vo SF01Vo) throws Exception {
		return delete(namespace + ".delete", SF01Vo);
	}

	public SF01Vo selectFile(SF01Vo SF01Vo) throws Exception {
		return (SF01Vo) selectOne(namespace + ".selectFile", SF01Vo);
	}

	public int likelogSF01Vo(SF01Vo SF01Vo) throws Exception {
		return update(namespace + ".likelog", SF01Vo);
	}

	public int hatelogSF01Vo(SF01Vo SF01Vo) throws Exception {
		return update(namespace + ".hatelog", SF01Vo);
	}
}