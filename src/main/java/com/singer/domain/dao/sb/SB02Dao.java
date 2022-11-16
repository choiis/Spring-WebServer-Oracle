package com.singer.domain.dao.sb;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sb.SB02Entity;

@Repository("sb02Dao")
public class SB02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SB02";

	public int insertSB02(SB02Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int likeSB02(SB02Entity entity) throws Exception {
		return update(namespace + ".like", entity);
	}

	public int hateSB02(SB02Entity entity) throws Exception {
		return update(namespace + ".hate", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SB02Entity> selectSB02(SB02Entity entity) throws Exception {
		return (List<SB02Entity>) selectList(namespace + ".select", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SB02Entity> selectReplySB02(SB02Entity entity) throws Exception {
		return (List<SB02Entity>) selectList(namespace + ".selectReply", entity);
	}

	public int selectSF02Count(SB02Entity entity) throws Exception {
		return selectCnt(namespace + ".selectTotal", entity);
	}

	public int updateSB02(SB02Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int deleteSB02(SB02Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SB02Entity checkUserSB02(SB02Entity entity) throws Exception {
		return (SB02Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int deleteSeqSB02(SB02Entity entity) throws Exception {
		return delete(namespace + ".delete_seq01", entity);
	}

	public int deleteChild(SB02Entity entity) throws Exception {
		return delete(namespace + ".deleteChild", entity);
	}
}
