package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SB02Vo;

@Repository("sb02Dao")
public class SB02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SB02";

	public int insertSB02Vo(SB02Vo sb02Vo) throws Exception {
		return insert(namespace + ".insert", sb02Vo);
	}

	public int likeSB02Vo(SB02Vo sb02Vo) throws Exception {
		return update(namespace + ".like", sb02Vo);
	}

	public int hateSB02Vo(SB02Vo sb02Vo) throws Exception {
		return update(namespace + ".hate", sb02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SB02Vo> selectSB02Vo(SB02Vo sb02Vo) throws Exception {
		return (List<SB02Vo>) selectList(namespace + ".select", sb02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SB02Vo> selectReplySB02Vo(SB02Vo sb02Vo) throws Exception {
		return (List<SB02Vo>) selectList(namespace + ".selectReply", sb02Vo);
	}

	public int selectSF02Total(SB02Vo sf02Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", sf02Vo);
	}

	public int updateSB02Vo(SB02Vo sb02Vo) throws Exception {
		return update(namespace + ".update", sb02Vo);
	}

	public int deleteSB02Vo(SB02Vo sb02Vo) throws Exception {
		return delete(namespace + ".delete", sb02Vo);
	}

	public int delete_seqSB02Vo(SB02Vo sb02Vo) throws Exception {
		return delete(namespace + ".delete_seq01", sb02Vo);
	}

	public int deleteChild(SB02Vo sb02Vo) throws Exception {
		return delete(namespace + ".deleteChild", sb02Vo);
	}
}
