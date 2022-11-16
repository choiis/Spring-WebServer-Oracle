package com.singer.domain.dao.sr;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sr.SR01Entity;

@Repository("sr01Dao")
public class SR01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR01";

	public int insertSR01(SR01Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int deleteSR01(SR01Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public int updateSR01(SR01Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int likeSR01(SR01Entity entity) throws Exception {
		return update(namespace + ".like", entity);
	}

	public int hateSR01(SR01Entity entity) throws Exception {
		return update(namespace + ".hate", entity);
	}

	public int clickSR01(SR01Entity entity) throws Exception {
		return update(namespace + ".click", entity);
	}

	public int likelogSR01(SR01Entity entity) throws Exception {
		return insert(namespace + ".likelog", entity);
	}

	public int hatelogSR01(SR01Entity entity) throws Exception {
		return insert(namespace + ".hatelog", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SR01Entity> selectSR01(SR01Entity entity) throws Exception {
		return (List<SR01Entity>) selectList(namespace + ".select", entity);
	}

	public SR01Entity selectOneSR01(SR01Entity entity) throws Exception {
		return (SR01Entity) selectOne(namespace + ".selectOne", entity);
	}

	public SR01Entity checkUserSR01(SR01Entity entity) throws Exception {
		return (SR01Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int insertImage(List<SR01Entity> list) throws Exception {
		return insert(namespace + ".insertPhoto", list);
	}

	public String selectPhoto(SR01Entity entity) throws Exception {
		return (String) selectOne(namespace + ".selectPhoto", entity);
	}
}