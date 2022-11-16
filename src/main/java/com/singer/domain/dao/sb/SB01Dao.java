package com.singer.domain.dao.sb;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sb.SB01Entity;

@Repository("sb01Dao")
public class SB01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SB01";

	public int insertSB01(SB01Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SB01Entity> selectSB01(SB01Entity entity) throws Exception {
		return (List<SB01Entity>) selectList(namespace + ".select", entity);
	}

	public SB01Entity selectSB01Count() throws Exception {
		return (SB01Entity) selectOne(namespace + ".selectCount");
	}

	@SuppressWarnings("unchecked")
	public List<SB01Entity> selectFindSB01(SB01Entity entity) throws Exception {
		return (List<SB01Entity>) selectList(namespace + ".selectFind", entity);
	}

	public SB01Entity selectOneSB01(SB01Entity entity) throws Exception {
		return (SB01Entity) selectOne(namespace + ".selectOne", entity);
	}

	public SB01Entity checkUserSB01(SB01Entity entity) throws Exception {
		return (SB01Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int updateSB01(SB01Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int likeSB01(SB01Entity entity) throws Exception {
		return insert(namespace + ".like", entity);
	}

	public int hateSB01(SB01Entity entity) throws Exception {
		return insert(namespace + ".hate", entity);
	}

	public int clickSB01(SB01Entity entity) throws Exception {
		return update(namespace + ".click", entity);
	}

	public int deleteSB01(SB01Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public int insertVideo(SB01Entity entity) throws Exception {
		return insert(namespace + ".insertVideo", entity);
	}

	public int updateVideo(SB01Entity entity) throws Exception {
		return update(namespace + ".updateVideo", entity);
	}

	public String selectVideo(SB01Entity entity) throws Exception {
		return (String) selectOne(namespace + ".selectVideo", entity);
	}

	public int likelogSB01(SB01Entity entity) throws Exception {
		return update(namespace + ".likelog", entity);
	}

	public int hatelogSB01(SB01Entity entity) throws Exception {
		return update(namespace + ".hatelog", entity);
	}
}