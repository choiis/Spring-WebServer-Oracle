package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SR03Vo;

@Repository("sr03Dao")
public class SR03Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR03";

	public int insertSR03Vo(SR03Vo sr03Vo) throws Exception {
		return insert(namespace + ".insert", sr03Vo);
	}

	public int likeSR03Vo(SR03Vo sr03Vo) throws Exception {
		return update(namespace + ".like", sr03Vo);
	}

	public int hateSR03Vo(SR03Vo sr03Vo) throws Exception {
		return update(namespace + ".hate", sr03Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Vo> selectSR03Vo(SR03Vo sr03Vo) throws Exception {
		return (List<SR03Vo>) selectList(namespace + ".select", sr03Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SR03Vo> selectReplySR03Vo(SR03Vo sr03Vo) throws Exception {
		return (List<SR03Vo>) selectList(namespace + ".selectReply", sr03Vo);
	}

	public int selectSR03Total(SR03Vo sr03Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", sr03Vo);
	}

	public int updateSR03Vo(SR03Vo sr03Vo) throws Exception {
		return update(namespace + ".update", sr03Vo);
	}

	public int deleteSR03Vo(SR03Vo sr03Vo) throws Exception {
		return delete(namespace + ".delete", sr03Vo);
	}

	public SR03Vo checkUserSR03Vo(SR03Vo sr03Vo) throws Exception {
		return (SR03Vo) selectOne(namespace + ".checkUser", sr03Vo);
	}

	public int delete_seqSR03Vo(SR03Vo sr03Vo) throws Exception {
		return delete(namespace + ".delete_seq01", sr03Vo);
	}

	public int deleteChild(SR03Vo sr03Vo) throws Exception {
		return delete(namespace + ".deleteChild", sr03Vo);
	}
}
