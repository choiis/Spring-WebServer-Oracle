package com.singer.domain.dao.sr;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sr.SR03Entity;

@Repository("sr03Dao")
public class SR03Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR03";

	public int insertSR03(SR03Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int likeSR03(SR03Entity entity) throws Exception {
		return update(namespace + ".like", entity);
	}

	public int hateSR03(SR03Entity entity) throws Exception {
		return update(namespace + ".hate", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Entity> selectSR03(SR03Entity entity) throws Exception {
		return (List<SR03Entity>) selectList(namespace + ".select", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Entity> selectReplySR03(SR03Entity entity) throws Exception {
		return (List<SR03Entity>) selectList(namespace + ".selectReply", entity);
	}

	public int selectSR03Count(SR03Entity entity) throws Exception {
		return selectCnt(namespace + ".selectTotal", entity);
	}

	public int updateSR03(SR03Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int deleteSR03(SR03Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SR03Entity checkUserSR03(SR03Entity entity) throws Exception {
		return (SR03Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int deleteSeqSR03(SR03Entity entity) throws Exception {
		return delete(namespace + ".delete_seq01", entity);
	}

	public int deleteChild(SR03Entity entity) throws Exception {
		return delete(namespace + ".deleteChild", entity);
	}
}
