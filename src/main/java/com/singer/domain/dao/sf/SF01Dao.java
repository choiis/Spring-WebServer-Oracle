package com.singer.domain.dao.sf;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sf.SF01Entity;

@Repository("sf01Dao")
public class SF01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SF01";

	public int insertSF01Vo(SF01Entity SF01Vo) throws Exception {
		return insert(namespace + ".insert", SF01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SF01Entity> selectSF01Vo(SF01Entity SF01Vo) throws Exception {
		return (List<SF01Entity>) selectList(namespace + ".select", SF01Vo);
	}

	public SF01Entity selectSF01Count() throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectCount");
	}

	@SuppressWarnings("unchecked")
	public List<SF01Entity> selectFindSF01Vo(SF01Entity SF01Vo) throws Exception {
		return (List<SF01Entity>) selectList(namespace + ".selectFind", SF01Vo);
	}

	public SF01Entity selectOneSF01Vo(SF01Entity SF01Vo) throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectOne", SF01Vo);
	}

	public SF01Entity checkUserSF01Vo(SF01Entity SF01Vo) throws Exception {
		return (SF01Entity) selectOne(namespace + ".checkUser", SF01Vo);
	}

	public int updateSF01Vo(SF01Entity SF01Vo) throws Exception {
		return update(namespace + ".update", SF01Vo);
	}

	public int likeSF01Vo(SF01Entity SF01Vo) throws Exception {
		return insert(namespace + ".like", SF01Vo);
	}

	public int hateSF01Vo(SF01Entity SF01Vo) throws Exception {
		return insert(namespace + ".hate", SF01Vo);
	}

	public int clickSF01Vo(SF01Entity SF01Vo) throws Exception {
		return update(namespace + ".click", SF01Vo);
	}

	public int deleteSF01Vo(SF01Entity SF01Vo) throws Exception {
		return delete(namespace + ".delete", SF01Vo);
	}

	public SF01Entity selectFile(SF01Entity SF01Vo) throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectFile", SF01Vo);
	}

	public int likelogSF01Vo(SF01Entity SF01Vo) throws Exception {
		return update(namespace + ".likelog", SF01Vo);
	}

	public int hatelogSF01Vo(SF01Entity SF01Vo) throws Exception {
		return update(namespace + ".hatelog", SF01Vo);
	}

	public int mergeSFD1Vo(SF01Entity sf01vo) throws Exception {
		return update(namespace + ".mergesf01", sf01vo);
	}
}