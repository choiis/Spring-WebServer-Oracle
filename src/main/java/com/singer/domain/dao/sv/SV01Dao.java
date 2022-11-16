package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sv.SV01Entity;

@Repository("sv01Dao")
public class SV01Dao extends SuperDao {
	private static final String namespace = "com.singer.mappers.SV01";

	public int insertSV01(SV01Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int updateSV01(SV01Entity entity) throws Exception {
		return insert(namespace + ".update", entity);
	}

	public int clickSV01(SV01Entity entity) throws Exception {
		return insert(namespace + ".click", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SV01Entity> selectSV01(SV01Entity entity) throws Exception {
		return (List<SV01Entity>) selectList(namespace + ".select", entity);
	}

	public SV01Entity selectOneSV01(SV01Entity entity) throws Exception {
		return (SV01Entity) selectOne(namespace + ".selectOne", entity);
	}

	public SV01Entity checkUserSV01(SV01Entity entity) throws Exception {
		return (SV01Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int deleteSV01(SV01Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public int likeSV01(SV01Entity entity) throws Exception {
		return insert(namespace + ".like", entity);
	}

	public int hateSV01(SV01Entity entity) throws Exception {
		return insert(namespace + ".hate", entity);
	}

	public int likelogSV01(SV01Entity entity) throws Exception {
		return update(namespace + ".likelog", entity);
	}

	public int hatelogSV01(SV01Entity entity) throws Exception {
		return update(namespace + ".hatelog", entity);
	}
}
