package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.vo.sv.SV04Vo;

@Repository("sv04Dao")
public class SV04Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SV04";

	public int insertSV04Vo(SV04Vo sv04Vo) throws Exception {
		return insert(namespace + ".insert", sv04Vo);
	}

	public int likeSV04Vo(SV04Vo sv04Vo) throws Exception {
		return update(namespace + ".like", sv04Vo);
	}

	public int hateSV04Vo(SV04Vo sv04Vo) throws Exception {
		return update(namespace + ".hate", sv04Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Vo> selectSV04Vo(SV04Vo sv04Vo) throws Exception {
		return (List<SV04Vo>) selectList(namespace + ".select", sv04Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV04Vo> selectReplySV04Vo(SV04Vo sv04Vo) throws Exception {
		return (List<SV04Vo>) selectList(namespace + ".selectReply", sv04Vo);
	}

	public int selectSV04Total(SV04Vo sv04Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", sv04Vo);
	}

	public int updateSV04Vo(SV04Vo sv04Vo) throws Exception {
		return update(namespace + ".update", sv04Vo);
	}

	public int deleteSV04Vo(SV04Vo sv04Vo) throws Exception {
		return delete(namespace + ".delete", sv04Vo);
	}

	public SV04Vo checkUserSV04Vo(SV04Vo sv04Vo) throws Exception {
		return (SV04Vo) selectOne(namespace + ".checkUser", sv04Vo);
	}

	public int delete_seqSV04Vo(SV04Vo sv04Vo) throws Exception {
		return delete(namespace + ".delete_seq01", sv04Vo);
	}

	public int deleteChild(SV04Vo sv04Vo) throws Exception {
		return delete(namespace + ".deleteChild", sv04Vo);
	}
}
