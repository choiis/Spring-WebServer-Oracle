package com.singer.domain.dao.sb;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sb.SB01Entity;

@Repository("sb01Dao")
public class SB01Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SB01";

	public int insertSB01Vo(SB01Entity sb01Vo) throws Exception {
		return insert(namespace + ".insert", sb01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SB01Entity> selectSB01Vo(SB01Entity sb01Vo) throws Exception {
		return (List<SB01Entity>) selectList(namespace + ".select", sb01Vo);
	}

	public SB01Entity selectSB01VoCount() throws Exception {
		return (SB01Entity) selectOne(namespace + ".selectCount");
	}

	@SuppressWarnings("unchecked")
	public List<SB01Entity> selectFindSB01Vo(SB01Entity sb01Vo) throws Exception {
		return (List<SB01Entity>) selectList(namespace + ".selectFind", sb01Vo);
	}

	public SB01Entity selectOneSB01Vo(SB01Entity sb01Vo) throws Exception {
		return (SB01Entity) selectOne(namespace + ".selectOne", sb01Vo);
	}

	public SB01Entity checkUserSB01Vo(SB01Entity sb01Vo) throws Exception {
		return (SB01Entity) selectOne(namespace + ".checkUser", sb01Vo);
	}

	public int updateSB01Vo(SB01Entity sb01Vo) throws Exception {
		return update(namespace + ".update", sb01Vo);
	}

	public int likeSB01Vo(SB01Entity sb01Vo) throws Exception {
		return insert(namespace + ".like", sb01Vo);
	}

	public int hateSB01Vo(SB01Entity sb01Vo) throws Exception {
		return insert(namespace + ".hate", sb01Vo);
	}

	public int clickSB01Vo(SB01Entity sb01Vo) throws Exception {
		return update(namespace + ".click", sb01Vo);
	}

	public int deleteSB01Vo(SB01Entity sb01Vo) throws Exception {
		return delete(namespace + ".delete", sb01Vo);
	}

	public int insertVideo(SB01Entity sb01Vo) throws Exception {
		return insert(namespace + ".insertVideo", sb01Vo);
	}

	public int updateVideo(SB01Entity sb01Vo) throws Exception {
		return update(namespace + ".updateVideo", sb01Vo);
	}

	public String selectVideo(SB01Entity sb01Vo) throws Exception {
		return (String) selectOne(namespace + ".selectVideo", sb01Vo);
	}

	public int likelogSB01Vo(SB01Entity sb01Vo) throws Exception {
		return update(namespace + ".likelog", sb01Vo);
	}

	public int hatelogSB01Vo(SB01Entity sb01Vo) throws Exception {
		return update(namespace + ".hatelog", sb01Vo);
	}
}