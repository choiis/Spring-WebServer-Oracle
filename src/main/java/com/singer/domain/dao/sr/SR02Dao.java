package com.singer.domain.dao.sr;

import com.singer.domain.dao.SuperDao;
import org.springframework.stereotype.Repository;

import com.singer.domain.entity.sr.SR01Entity;
import com.singer.domain.entity.sr.SR02Entity;

@Repository("sr02Dao")
public class SR02Dao extends SuperDao {

	private static final String namespace = "com.singer.mappers.SR02";

	public int insertSR02Vo(SR02Entity sr01Vo) throws Exception {
		return insert(namespace + ".insert", sr01Vo);
	}

	public int deleteSR02Vo(SR01Entity sr01Vo) throws Exception {
		return delete(namespace + ".delete", sr01Vo);
	}

	public SR01Entity selectOneSR02Vo(SR01Entity sr01Vo) throws Exception {
		return (SR01Entity) selectOne(namespace + ".selectOne", sr01Vo);
	}

	public SR01Entity selectGradeSR02Vo(SR01Entity sr01Vo) throws Exception {
		return (SR01Entity) selectOne(namespace + ".selectGrade", sr01Vo);
	}
}