package com.singer.domain.dao.sf;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sf.SF02Entity;

@Repository("sf02Dao")
public class SF02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SF02";

	public int insertSF02(SF02Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int likeSF02(SF02Entity entity) throws Exception {
		return update(namespace + ".like", entity);
	}

	public int hateSF02(SF02Entity entity) throws Exception {
		return update(namespace + ".hate", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SF02Entity> selectSF02(SF02Entity entity) throws Exception {
		return (List<SF02Entity>) selectList(namespace + ".select", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SF02Entity> selectReplySF02(SF02Entity entity) throws Exception {
		return (List<SF02Entity>) selectList(namespace + ".selectReply", entity);
	}

	public int selectSF02Count(SF02Entity entity) throws Exception {
		return selectCnt(namespace + ".selectTotal", entity);
	}

	public int updateSF02(SF02Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int deleteSF02(SF02Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SF02Entity checkUserSF02(SF02Entity entity) throws Exception {
		return (SF02Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int deleteSeqSF02(SF02Entity entity) throws Exception {
		return delete(namespace + ".delete_seq01", entity);
	}

	public int deleteChild(SF02Entity entity) throws Exception {
		return delete(namespace + ".deleteChild", entity);
	}
}
