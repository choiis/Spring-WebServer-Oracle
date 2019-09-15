package com.singer.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.vo.SV04Vo;

@Repository("sv04Dao")
public class SV04Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SV04";

	public int insertSV04Vo(SV04Vo SV04Vo) throws Exception {
		return insert(namespace + ".insert", SV04Vo);
	}

	public int likeSV04Vo(SV04Vo SV04Vo) throws Exception {
		return update(namespace + ".like", SV04Vo);
	}

	public int hateSV04Vo(SV04Vo SV04Vo) throws Exception {
		return update(namespace + ".hate", SV04Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Vo> selectSV04Vo(SV04Vo SV04Vo) throws Exception {
		return (List<SV04Vo>) selectList(namespace + ".select", SV04Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Vo> selectReplySV04Vo(SV04Vo SV04Vo) throws Exception {
		return (List<SV04Vo>) selectList(namespace + ".selectReply", SV04Vo);
	}

	public int selectSV04Total(SV04Vo SV04Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", SV04Vo);
	}

	public int updateSV04Vo(SV04Vo SV04Vo) throws Exception {
		return update(namespace + ".update", SV04Vo);
	}

	public int deleteSV04Vo(SV04Vo SV04Vo) throws Exception {
		return delete(namespace + ".delete", SV04Vo);
	}

	public int delete_seqSV04Vo(SV04Vo SV04Vo) throws Exception {
		return delete(namespace + ".delete_seq01", SV04Vo);
	}

	public int deleteChild(SV04Vo SV04Vo) throws Exception {
		return delete(namespace + ".deleteChild", SV04Vo);
	}
}
