package com.singer.domain.dao.sf;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.vo.sf.SF02Vo;

@Repository("sf02Dao")
public class SF02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SF02";

	public int insertSF02Vo(SF02Vo sf02Vo) throws Exception {
		return insert(namespace + ".insert", sf02Vo);
	}

	public int likeSF02Vo(SF02Vo sf02Vo) throws Exception {
		return update(namespace + ".like", sf02Vo);
	}

	public int hateSF02Vo(SF02Vo sf02Vo) throws Exception {
		return update(namespace + ".hate", sf02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SF02Vo> selectSF02Vo(SF02Vo sf02Vo) throws Exception {
		return (List<SF02Vo>) selectList(namespace + ".select", sf02Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SF02Vo> selectReplySF02Vo(SF02Vo sf02Vo) throws Exception {
		return (List<SF02Vo>) selectList(namespace + ".selectReply", sf02Vo);
	}

	public int selectSF02Total(SF02Vo sf02Vo) throws Exception {
		return selectCnt(namespace + ".selectTotal", sf02Vo);
	}

	public int updateSF02Vo(SF02Vo sf02Vo) throws Exception {
		return update(namespace + ".update", sf02Vo);
	}

	public int deleteSF02Vo(SF02Vo sf02Vo) throws Exception {
		return delete(namespace + ".delete", sf02Vo);
	}

	public SF02Vo checkUserSF02Vo(SF02Vo sf02Vo) throws Exception {
		return (SF02Vo) selectOne(namespace + ".checkUser", sf02Vo);
	}

	public int delete_seqSF02Vo(SF02Vo sf02Vo) throws Exception {
		return delete(namespace + ".delete_seq01", sf02Vo);
	}

	public int deleteChild(SF02Vo sf02Vo) throws Exception {
		return delete(namespace + ".deleteChild", sf02Vo);
	}
}
