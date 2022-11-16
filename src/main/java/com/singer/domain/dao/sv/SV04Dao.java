package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sv.SV04Entity;

@Repository("sv04Dao")
public class SV04Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SV04";

	public int insertSV04(SV04Entity entity) throws Exception {
		return insert(namespace + ".insert", entity);
	}

	public int likeSV04(SV04Entity entity) throws Exception {
		return update(namespace + ".like", entity);
	}

	public int hateSV04(SV04Entity entity) throws Exception {
		return update(namespace + ".hate", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Entity> selectSV04(SV04Entity entity) throws Exception {
		return (List<SV04Entity>) selectList(namespace + ".select", entity);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Entity> selectReplySV04(SV04Entity entity) throws Exception {
		return (List<SV04Entity>) selectList(namespace + ".selectReply", entity);
	}

	public int selectSV04Count(SV04Entity entity) throws Exception {
		return selectCnt(namespace + ".selectTotal", entity);
	}

	public int updateSV04(SV04Entity entity) throws Exception {
		return update(namespace + ".update", entity);
	}

	public int deleteSV04(SV04Entity entity) throws Exception {
		return delete(namespace + ".delete", entity);
	}

	public SV04Entity checkUserSV04(SV04Entity entity) throws Exception {
		return (SV04Entity) selectOne(namespace + ".checkUser", entity);
	}

	public int deleteSeqSV04(SV04Entity entity) throws Exception {
		return delete(namespace + ".delete_seq01", entity);
	}

	public int deleteChild(SV04Entity entity) throws Exception {
		return delete(namespace + ".deleteChild", entity);
	}
}
