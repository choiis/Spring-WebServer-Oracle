package com.singer.domain.dao.sv;

import com.singer.domain.dao.SuperDao;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sv.SV01Entity;

@Repository("sv01Dao")
public class SV01Dao extends SuperDao {
	private static final String namespace = "com.singer.mappers.SV01";

	public int insertSV01Vo(SV01Entity sv01Vo) throws Exception {
		return insert(namespace + ".insert", sv01Vo);
	}

	public int updateSV01Vo(SV01Entity sv01Vo) throws Exception {
		return insert(namespace + ".update", sv01Vo);
	}

	public int clickSV01Vo(SV01Entity sv01Vo) throws Exception {
		return insert(namespace + ".click", sv01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV01Entity> selectSV01Vo(SV01Entity sv01Vo) throws Exception {
		return (List<SV01Entity>) selectList(namespace + ".select", sv01Vo);
	}

	@SuppressWarnings("unchecked")
	public List<SV01Entity> selectFindSV01Vo(SV01Entity sv01Vo) throws Exception {
		return (List<SV01Entity>) selectList(namespace + ".selectFind", sv01Vo);
	}

	public SV01Entity selectOneSV01Vo(SV01Entity sv01Vo) throws Exception {
		return (SV01Entity) selectOne(namespace + ".selectOne", sv01Vo);
	}

	public SV01Entity checkUserV01Vo(SV01Entity sv01Vo) throws Exception {
		return (SV01Entity) selectOne(namespace + ".checkUser", sv01Vo);
	}

	public int deleteSV01Vo(SV01Entity sv01Vo) throws Exception {
		return delete(namespace + ".delete", sv01Vo);
	}

	public int likeSV01Vo(SV01Entity sv01Vo) throws Exception {
		return insert(namespace + ".like", sv01Vo);
	}

	public int hateSV01Vo(SV01Entity sv01Vo) throws Exception {
		return insert(namespace + ".hate", sv01Vo);
	}

	public int likelogSV01Vo(SV01Entity sv01Vo) throws Exception {
		return update(namespace + ".likelog", sv01Vo);
	}

	public int hatelogSV01Vo(SV01Entity sv01Vo) throws Exception {
		return update(namespace + ".hatelog", sv01Vo);
	}
}
