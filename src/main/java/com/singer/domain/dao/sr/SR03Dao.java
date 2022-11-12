package com.singer.domain.dao.sr;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sr.SR03Entity;

@Repository("sr03Dao")
public class SR03Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR03";

	public int insertSR03Vo(SR03Entity sr03Vo) throws Exception {
		return insert(namespace + ".insert", sr03Vo);
	}

	public int likeSR03Vo(SR03Entity sr03Vo) throws Exception {
		return update(namespace + ".like", sr03Vo);
	}

	public int hateSR03Vo(SR03Entity sr03Vo) throws Exception {
		return update(namespace + ".hate", sr03Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Entity> selectSR03Vo(SR03Entity sr03Vo) throws Exception {
		return (List<SR03Entity>) selectList(namespace + ".select", sr03Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Entity> selectReplySR03Vo(SR03Entity sr03Vo) throws Exception {
		return (List<SR03Entity>) selectList(namespace + ".selectReply", sr03Vo);
	}

	public int selectSR03Total(SR03Entity sr03Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", sr03Vo);
	}

	public int updateSR03Vo(SR03Entity sr03Vo) throws Exception {
		return update(namespace + ".update", sr03Vo);
	}

	public int deleteSR03Vo(SR03Entity sr03Vo) throws Exception {
		return delete(namespace + ".delete", sr03Vo);
	}

	public SR03Entity checkUserSR03Vo(SR03Entity sr03Vo) throws Exception {
		return (SR03Entity) selectOne(namespace + ".checkUser", sr03Vo);
	}

	public int delete_seqSR03Vo(SR03Entity sr03Vo) throws Exception {
		return delete(namespace + ".delete_seq01", sr03Vo);
	}

	public int deleteChild(SR03Entity sr03Vo) throws Exception {
		return delete(namespace + ".deleteChild", sr03Vo);
	}
}
