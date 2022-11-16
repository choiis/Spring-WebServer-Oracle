package com.singer.domain.dao.sf;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sf.SF01Entity;

@Repository("sf01Dao")
public class SF01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SF01";

	public int insertSF01(SF01Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SF01Entity> selectSF01(SF01Entity entity) throws Exception {
		return (List<SF01Entity>) selectList(namespace + ".select", entity);
	}

	public SF01Entity selectSF01Count() throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectCount");
	}


	public SF01Entity selectOneSF01(SF01Entity entity) throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectOne", entity);
	}

	public SF01Entity checkUserSF01(SF01Entity entity) throws Exception {
		return (SF01Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int updateSF01(SF01Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int likeSF01(SF01Entity entity) throws Exception {
		return insert(namespace + ".like", entity);
	}

	public int hateSF01(SF01Entity entity) throws Exception {
		return insert(namespace + ".hate", entity);
	}

	public int clickSF01(SF01Entity entity) throws Exception {
		return update(namespace + ".click", entity);
	}

	public int deleteSF01(SF01Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SF01Entity selectFile(SF01Entity entity) throws Exception {
		return (SF01Entity) selectOne(namespace + ".selectFile", entity);
	}

	public int likelogSF01(SF01Entity entity) throws Exception {
		return update(namespace + ".likelog", entity);
	}

	public int hatelogSF01(SF01Entity entity) throws Exception {
		return update(namespace + ".hatelog", entity);
	}

	public int mergeSFD1(SF01Entity entity) throws Exception {
		return update(namespace + ".mergesf01", entity);
	}
}